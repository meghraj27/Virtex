package com.meghrajswami.virtex.domain;

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
public class Holding {

    // @Id
    @Column(name = "SYMBOL")
    @Enumerated(EnumType.STRING)
    private Symbol symbol;

    @Column(name = "QUANTITY", precision = 19, scale = 8)
    private BigDecimal quantity;

    @Column(name = "AVAILABLE_QUANTITY", precision = 19, scale = 8)
    private BigDecimal availableQuantity;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED", nullable = false)
    private Date updated = new Date();

    protected Holding() {
    }

    public Holding(Symbol symbol, BigDecimal quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.availableQuantity = quantity;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
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
