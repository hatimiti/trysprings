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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@Sql("/init.sql")
@SpringBootTest
@SpringJUnitConfig
public class SampleDataJpaServiceTest {

    @Autowired
    SampleDataJpaService sampleDataJpaService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PlanRepository planRepository;
    @Autowired
    ReserveRepository reserveRepository;
    @Autowired
    ReserveItemRepository reserveItemRepository;
    @Autowired
    EntityManager entityManager;

    @Transactional
    @Test
    public void testSaveUser() {
        User u = new User();
        u.name = "Test User 1";
        u.password = u.name;
        u = this.userRepository.save(u);
        assertNotNull(u.userId, "[Test] u.userId = " + u.userId);

        List<User> users = userRepository.findAll();
        assertEquals(3, users.size(), "[Test] " + users.toString());
    }

    @Transactional
    @Test
    public void testSaveReserve() {
        final User user = userRepository.findAll().get(0);
        final List<Plan> plans = planRepository.findAll();
        final Reserve r = sampleDataJpaService.reserve(
                user, Arrays.asList(plans.get(1), plans.get(0), plans.get(2)));

        entityManager.flush();
        final Reserve rr = this.reserveRepository.findById(r.reserveId).get();
        System.out.println("[Test]" + r);
        System.out.println("[Test]" + rr);
        assertEquals(3, rr.reserveItems.size());
    }

    @Transactional
    @Test
    public void testFindReserveByIdWithJDBC() {
        final Reserve r = sampleDataJpaService.findReserveByIdWithJDBC(2L).get();
        assertEquals(1L, (long) r.userId);
    }

    @Transactional
    @Test
    public void testFindReserveById() {
        final Reserve r = sampleDataJpaService.findReserveById(2L).get();
        assertEquals(new ReserveNo("NIlasd;aai1"), r.reserveNo);

        assertNotNull(r.reserveItems); // ここではitemのSQL発行されない
        for (final ReserveItem item : r.reserveItems) {
            // ここでitemのSQLが実行される
            assertNotNull(item);
        }
    }

    /*
     * N+1問題
     */
    @Transactional
    @Test
    public void testFindReservesForProblemOfNplus1() {
        final List<Reserve> reserves = this.reserveRepository.findAll();
        reserves.stream()
                .forEach(r -> r.reserveItems.stream()
                .forEach(i -> System.out.println(i)));
    }

    /*
     * ネイティブクエリ実行 Entity 定義
     */
    @Transactional
    @Test
    public void testFindReserveListByExistsUser() {
        List<Reserve> reserves = this.reserveRepository.findListByExistsUser(1L);
        assertEquals(2, reserves.size());
        reserves.forEach(r -> {
            assertNotEquals(0, r.reserveItems.size());
            System.out.println("[Test] " + r);
        });
    }

    /*
     * ネイティブクエリ実行 jpa-named-queries.properties 定義
     */
    @Transactional
    @Test
    public void testFindReserveItemListByExistsUser() {
        final List<ReserveItem> ritems = this.reserveItemRepository
                .findListByExistsPlanId(1L);
        assertEquals(6, ritems.size());
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
