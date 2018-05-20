package com.github.hatimiti.spring.data.jpa.db.entity;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "M_PLAN")
public class Plan implements Serializable {

    /* The columns */

    @Id
    @GeneratedValue
    @Column(name = "M_PLAN_ID")
    public Long planId;

    @Column(name = "PLAN_NAME")
    public String planName;

    @Column(name = "CONTENT")
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
