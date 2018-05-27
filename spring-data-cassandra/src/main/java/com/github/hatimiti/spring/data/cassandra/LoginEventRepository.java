package com.github.hatimiti.spring.data.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface LoginEventRepository extends CassandraRepository<LoginEvent, LoginEventKey> {

    @Query("Select * From login_event Where person_id = ?0 And event_time = ?1")
    Optional<LoginEvent> findByPersonIdAndEventTime(String personId, Date eventTime);

}
