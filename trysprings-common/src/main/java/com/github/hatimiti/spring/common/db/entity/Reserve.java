package com.github.hatimiti.spring.common.db.entity;

import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;
import java.util.List;

public class Reserve implements Serializable {

    /* The columns */

    public Long reserveId;

    public String reserveNo;

    public Long userId;

    /* The associations */

    public User user;

    public List<ReserveItem> reserveItems;

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("reserveId", reserveId)
                .append("reserveNo", reserveNo)
                .append("userId", userId)
                .append("[user]", user)
                .append("[reserveItems]", reserveItems)
                .toString();
    }
}
