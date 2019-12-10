package com.meghrajswami.virtex.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by megh on 6/28/2016.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`TRADE_TRANSACTION`",
        indexes = {
                @Index(columnList = "BID_BY_ID"),
                @Index(columnList = "BID_TRADE_ORDER_ID"),
                @Index(columnList = "ASK_BY_ID"),
                @Index(columnList = "ASK_TRADE_ORDER_ID")
        }
)
public class TradeTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "BID_BY_ID")
    private Long bidById;

    @Column(name = "BID_TRADE_ORDER_ID")
    private Long bidTradeOrderId;

    @Column(name = "ASK_BY_ID")
    private Long askById;

    @Column(name = "ASK_TRADE_ORDER_ID")
    private Long askTradeOrderId;

    @Column(name = "SYMBOL")
    @Enumerated(EnumType.STRING)
    private Symbol symbol;

    @Column(name = "QUANTITY", precision = 19, scale = 8)
    private BigDecimal quantity;

    @Column(name = "PRICE", precision = 19, scale = 4)
    private BigDecimal price;

    @Column(name = "VALUE", precision = 19, scale = 4)
    private BigDecimal value;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created = new Date();

    //    @CreatedBy
    //    @Column(name = "CREATED_BY")
    //    private Long createdBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated = new Date();

    //    @LastModifiedBy
    //    @Column(name = "UPDATED_BY")
    //    private Long updatedBy;


    protected TradeTransaction() {
    }

    public TradeTransaction(Long bidById, Long bidTradeOrderId, Long askById, Long askTradeOrderId, Symbol symbol, BigDecimal quantity, BigDecimal price) {
        this.bidById = bidById;
        this.bidTradeOrderId = bidTradeOrderId;
        this.askById = askById;
        this.askTradeOrderId = askTradeOrderId;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.value = price.multiply(quantity);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBidById() {
        return bidById;
    }

    public void setBidById(Long bidById) {
        this.bidById = bidById;
    }

    public Long getBidTradeOrderId() {
        return bidTradeOrderId;
    }

    public void setBidTradeOrderId(Long bidTradeOrderId) {
        this.bidTradeOrderId = bidTradeOrderId;
    }

    public Long getAskById() {
        return askById;
    }

    public void setAskById(Long askById) {
        this.askById = askById;
    }

    public Long getAskTradeOrderId() {
        return askTradeOrderId;
    }

    public void setAskTradeOrderId(Long askTradeOrderId) {
        this.askTradeOrderId = askTradeOrderId;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
