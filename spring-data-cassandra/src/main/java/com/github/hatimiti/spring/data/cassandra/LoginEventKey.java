package com.github.hatimiti.spring.data.cassandra;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@PrimaryKeyClass
public class LoginEventKey implements Serializable {

    @PrimaryKeyColumn(name = "person_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    public String personId;

    @PrimaryKeyColumn(name = "event_time", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    public Date eventTime;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventTime == null) ? 0 : eventTime.hashCode());
        result = prime * result + ((personId == null) ? 0 : personId.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final LoginEventKey other = (LoginEventKey) obj;
        return Objects.equals(eventTime, other.eventTime)
                && Objects.equals(personId, other.personId);
    }
}
