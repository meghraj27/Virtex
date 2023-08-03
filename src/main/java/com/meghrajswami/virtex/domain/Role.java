package com.meghrajswami.virtex.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * Created by Meghraj on 7/30/2017.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`ROLE`",
        indexes = {
                @Index(columnList = "NAME")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends IdentifiableEntity {

    @Column(name = "NAME", length = 50)
    private String name;

    public Role(String name) {
        this.name = name;
    }

}
