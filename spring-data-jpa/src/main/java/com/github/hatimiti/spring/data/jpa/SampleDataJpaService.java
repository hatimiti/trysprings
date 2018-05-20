package com.github.hatimiti.spring.data.jpa;

import com.github.hatimiti.spring.data.jpa.db.entity.Plan;
import com.github.hatimiti.spring.data.jpa.db.entity.Reserve;
import com.github.hatimiti.spring.data.jpa.db.entity.User;

import java.util.List;
import java.util.Optional;

public interface SampleDataJpaService {

    Reserve reserve(User user, List<Plan> plans);

    Optional<Reserve> findReserveById(final Long reserveId);
    Optional<Reserve> findReserveByIdWithJDBC(final Long reserveId);

}
