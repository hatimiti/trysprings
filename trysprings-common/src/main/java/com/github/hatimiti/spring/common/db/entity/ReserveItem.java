package com.github.hatimiti.spring.common.db.entity;

import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;

public class ReserveItem implements Serializable {

    public Long reserveItemId;

    public Long reserveId;

    public Long planId;

    public String note;

    /* The associations. */

    public Plan plan;

    public Reserve reserve;

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("reserveItemId", reserveItemId)
                .append("reserveId", reserveId)
                .append("planId", planId)
                .append("note", note)
                .append("[plan]", plan)
                .toString();
    }
}
