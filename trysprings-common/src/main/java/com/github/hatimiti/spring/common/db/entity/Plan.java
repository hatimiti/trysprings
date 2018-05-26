package com.github.hatimiti.spring.common.db.entity;

import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;

public class Plan implements Serializable {

    /* The columns */

    public Long planId;

    public String planName;

    public String content;

    /* The associations */

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("planId", planId)
                .append("planName", planName)
                .append("content", content)
                .toString();
    }
}
