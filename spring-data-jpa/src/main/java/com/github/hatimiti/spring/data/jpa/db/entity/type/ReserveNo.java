package com.github.hatimiti.spring.data.jpa.db.entity.type;

import com.github.hatimiti.spring.data.jpa.Type;

import javax.persistence.Embeddable;

@Embeddable
public class ReserveNo extends Type<String> {

    private String val;

    // For JPA
    private ReserveNo() { }

    public ReserveNo(final String reserveNo) {
        this.val = reserveNo;
    }

    @Override
    public String getVal() {
        return val;
    }
}
