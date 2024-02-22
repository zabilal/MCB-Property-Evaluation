package com.mcbproperty.authservice.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Data
//@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean active;
    private Boolean deleted;
    @Temporal(TemporalType.DATE)
    private Date createDate;
    @Temporal(TemporalType.DATE)
    private Date lastModified;

    @CreatedBy
    @ManyToOne
    private User createdBy;

    @LastModifiedBy
    @ManyToOne
    private User LastModifiedBy;

    @PrePersist
    public void setCreateDate () {
        this.active = Boolean.TRUE;
        this.deleted = Boolean.FALSE;
        this.createDate = this.lastModified = new Date();
    }

    @PreUpdate
    public void setLastUpdated () {
        this.lastModified = new Date();
    }
}
