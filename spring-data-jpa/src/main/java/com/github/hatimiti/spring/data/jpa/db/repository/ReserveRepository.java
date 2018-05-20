package com.github.hatimiti.spring.data.jpa.db.repository;

import com.github.hatimiti.spring.data.jpa.db.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {

    // NOTE: NativeSQL
    @Query(name = "findListByExistsUser")
    List<Reserve> findListByExistsUser(@Param("userId") final Long userId);

}
