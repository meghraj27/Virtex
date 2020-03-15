package com.meghrajswami.virtex.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;

/**
 * The base class for auditable user entity.
 *
 * @author Meghraj
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AuditableUserEntity extends AuditableEntity {
    /**
     * The username of the creator of the entity.
     */
    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    /**
     * The username of the last modifier of the entity.
     */
    @LastModifiedBy
    @Column(name = "UPDATED_BY")
    private String updatedBy;
}

