package com.meghrajswami.virtex.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by megh on 6/28/2016.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)

// @EntityListeners(AuditingEntityListener.class)
// @Table(name = "`HOLDING`",
//         indexes = {
//                 // @Index(columnList = "SYMBOL"),
//                 // @Index(columnList = "QUANTITY"),
//         }
// )
@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Holding {

    @Enumerated(EnumType.STRING)
    private Symbol symbol;

    @Column(precision = 19, scale = 8)
    private BigDecimal quantity;

    @Column(precision = 19, scale = 8)
    private BigDecimal availableQuantity;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated = new Date();

    public Holding(Symbol symbol, BigDecimal quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.availableQuantity = quantity;
    }

    public void block(BigDecimal quantity) {
        availableQuantity = availableQuantity.subtract(quantity);
    }

    public void release(BigDecimal quantity) {
        availableQuantity = availableQuantity.add(quantity);
    }

    /*
    add quantity to holding, use only when trade happens
     */
    public void add(BigDecimal quantity) {
        this.quantity = this.quantity.add(quantity);
        availableQuantity = availableQuantity.add(quantity);

    }

    /*
    remove quantity from holding, use only when trade happens
     */
    public void remove(BigDecimal quantity) {
        this.quantity = this.quantity.subtract(quantity);
    }

}
