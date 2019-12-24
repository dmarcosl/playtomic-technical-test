package com.playtomic.tests.wallet.model.db;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditionEntity implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    @CreatedDate
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    @LastModifiedDate
    private Date updateDate;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
