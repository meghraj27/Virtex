package com.meghrajswami.virtex.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.MappedSuperclass;

/**
 * The base class for auditable user entity.
 *
 * @author Meghraj
 * @version 1.0
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AuditableUserEntity extends AuditableEntity {
    /**
     * The username of the creator of the entity.
     */
    @CreatedBy
    private String createdBy;

    /**
     * The username of the last modifier of the entity.
     */
    @LastModifiedBy
    private String updatedBy;
}

