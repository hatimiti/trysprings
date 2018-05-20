package com.github.hatimiti.spring.data.jpa;

import com.github.hatimiti.spring.data.jpa.db.entity.Plan;
import com.github.hatimiti.spring.data.jpa.db.entity.Reserve;
import com.github.hatimiti.spring.data.jpa.db.entity.ReserveItem;
import com.github.hatimiti.spring.data.jpa.db.entity.User;
import com.github.hatimiti.spring.data.jpa.db.entity.type.ReserveNo;
import com.github.hatimiti.spring.data.jpa.db.repository.PlanRepository;
import com.github.hatimiti.spring.data.jpa.db.repository.ReserveItemRepository;
import com.github.hatimiti.spring.data.jpa.db.repository.ReserveRepository;
import com.github.hatimiti.spring.data.jpa.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Transactional
@Service
public class SampleDataJpaServiceImpl implements SampleDataJpaService {

    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final ReserveRepository reserveRepository;
    private final ReserveItemRepository reserveItemRepository;

    public SampleDataJpaServiceImpl(
            final UserRepository userRepository,
            final PlanRepository planRepository,
            final ReserveRepository reserveRepository,
            final ReserveItemRepository reserveItemRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.reserveRepository = reserveRepository;
        this.reserveItemRepository = reserveItemRepository;
    }

    @Override
    public Reserve reserve(final User user, final List<Plan> plans) {

        // Create a reserve.
        final Reserve reserve = new Reserve();
        reserve.reserveNo = new ReserveNo(String.valueOf(new SecureRandom().nextInt()));
        reserve.userId = user.userId;
        reserve.user = user;
        final Reserve r = this.reserveRepository.save(reserve);
        // Create reserve items.
        r.reserveItems = new ArrayList<>();
        plans.stream()
                .forEach(p -> {
                    ReserveItem i = new ReserveItem();
                    i.plan = p;
                    i.planId = p.planId;
                    i.note = "sample note.";
                    i.reserve = r;
                    i.reserveId = r.reserveId;
                    r.reserveItems.add(this.reserveItemRepository.save(i));
                });
        return r;
    }

    @Override
    public Optional<Reserve> findReserveById(final Long reserveId) {
        return this.reserveRepository.findById(reserveId);
    }

    @Autowired
    DataSource dataSource;

    @Override
    public Optional<Reserve> findReserveByIdWithJDBC(final Long reserveId) {
        /*
         * JUnit で @Transactional を利用している場合はそのコネクションを取得しないと
         * @Sqlで投入したデータが SELECT できない。
         */
        final Connection conn = DataSourceUtils.getConnection(dataSource);
//        try (final Connection connection = dataSource.getConnection()) {
        try {
            final String sql = "Select * From T_RESERVE Where t_reserve_id = ?";
            final PreparedStatement ps = conn.prepareStatement(sql);
            int idx = 0;
            ps.setLong(++idx, reserveId);
            try (final ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("[Test] The result is empty.");
                    return empty();
                }
                final Reserve r = new Reserve();
                r.reserveId = rs.getLong("t_reserve_id");
                r.reserveNo = new ReserveNo(rs.getString("reserve_no"));
                r.userId = rs.getLong("m_user_id");
                if (rs.next()) {
                    throw new IllegalStateException("The result must be unique.");
                }
                return of(r);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("[ERROR]", e);
        }
    }

}
