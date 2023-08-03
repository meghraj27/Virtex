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
import java.math.BigDecimal;

/**
 * Created by megh on 6/28/2016.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`ORDER`", indexes = {
        @Index(columnList = "USER_ID"),
        @Index(columnList = "PAYMENT_REF_ID")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends AuditableUserEntity {

    @Column(name = "USER_ID")
    private Long userId;

    private String paymentMethodNonce;

    @Column(precision = 19, scale = 4)
    private BigDecimal amount;

    private Boolean paymentSuccess;

    @Column(name = "PAYMENT_REF_ID", length = 20)
    private String paymentRefId;

    private String data;

    public Order(Long userId, String paymentMethodNonce, BigDecimal amount) {
        this.userId = userId;
        this.paymentMethodNonce = paymentMethodNonce;
        this.amount = amount;
    }

}
