package com.github.hatimiti.spring.data.jpa.db.repository;

import com.github.hatimiti.spring.data.jpa.db.entity.ReserveItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserveItemRepository extends JpaRepository<ReserveItem, Long> {

    @Query(name = "ReserveItem.findListByExistsPlanId", nativeQuery = true)
    List<ReserveItem> findListByExistsPlanId(@Param("planId") final Long planId);

}
