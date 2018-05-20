package com.github.hatimiti.spring.data.jpa.db.entity;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "T_RESERVE_ITEM")
public class ReserveItem implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "T_RESERVE_ITEM_ID")
    public Long reserveItemId;

    @Column(name = "T_RESERVE_ID")
    public Long reserveId;

    @Column(name = "M_PLAN_ID")
    public Long planId;

    @Column(name = "NOTE")
    public String note;

    /* The associations. */

    @ManyToOne
    @JoinColumn(name = "M_PLAN_ID", referencedColumnName = "M_PLAN_ID", insertable = false, updatable = false)
    public Plan plan;

    @ManyToOne
    @JoinColumn(name = "T_RESERVE_ID", referencedColumnName = "T_RESERVE_ID", insertable = false, updatable = false)
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
