package com.github.hatimiti.spring.cassandra;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface LoginEventRepository extends CrudRepository<LoginEvent, LoginEventKey> {

    @Query("Select * From login_event Where person_id = ?0 And event_time = ?1")
    public Optional<LoginEvent> findByPersonIdAndEventTime(String personId, Date eventTime);

}
