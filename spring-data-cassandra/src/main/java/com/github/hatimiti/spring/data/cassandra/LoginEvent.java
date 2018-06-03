package com.github.hatimiti.spring.data.cassandra;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * create table login_event(
 *   person_id text,
 *   event_time timestamp,
 *   event_code int,
 *   ip_address text,
 *   primary key (person_id, event_time))
 *   with CLUSTERING ORDER BY (event_time DESC)
 * ;
 */
@Table("login_event")
public class LoginEvent {

    @PrimaryKey
    public LoginEventKey pk;

    @Column("event_code")
    public int eventCode;

    @Column("ip_address")
    public String ipAddress;

}
