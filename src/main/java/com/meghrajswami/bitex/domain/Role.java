package com.meghrajswami.bitex.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by megh on 7/30/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`ROLE`",
        indexes = {
                @Index(columnList = "NAME")})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME", length = 50)
    private String name;
}
