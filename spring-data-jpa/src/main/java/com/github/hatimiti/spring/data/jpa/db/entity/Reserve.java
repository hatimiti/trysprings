package com.github.hatimiti.spring.data.jpa.db.entity;

import com.github.hatimiti.spring.data.jpa.db.entity.type.ReserveNo;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@NamedNativeQueries({
    @NamedNativeQuery(name = "findListByExistsUser", resultClass = Reserve.class,
        query = "Select r.* From T_RESERVE r Where "
                + " Exists ( Select 'X' From M_USER u Where "
                    + " u.M_USER_ID = r.M_USER_ID And u.M_USER_ID = :userId )")
})
@Entity
@Table(name = "T_RESERVE")
public class Reserve implements Serializable {

    /* The columns */

    @Id
    @GeneratedValue
    @Column(name = "T_RESERVE_ID")
    public Long reserveId;

    @Embedded
    @AttributeOverride(column = @Column(name = "RESERVE_NO"), name = "val")
    public ReserveNo reserveNo;

    @Column(name = "M_USER_ID")
    public Long userId;

    /* The associations */

    @ManyToOne
    @JoinColumn(name = "M_USER_ID", referencedColumnName = "M_USER_ID", insertable = false, updatable = false)
    public User user;

    @OneToMany(mappedBy = "reserve")
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
