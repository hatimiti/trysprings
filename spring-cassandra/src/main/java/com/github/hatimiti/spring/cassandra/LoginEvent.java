package com.github.hatimiti.spring.cassandra;

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
@Table(value = "login_event")
public class LoginEvent {

    @PrimaryKey
    private LoginEventKey pk;

    @Column(value = "event_code")
    private int eventCode;

    @Column(value = "ip_address")
    private String ipAddress;

    public LoginEventKey getPk() {
        return pk;
    }

    public void setPk(LoginEventKey pk) {
        this.pk = pk;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
