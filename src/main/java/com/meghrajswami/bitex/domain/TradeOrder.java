package com.meghrajswami.bitex.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by megh on 6/28/2016.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`TRADE_ORDER`",
        indexes = {
                @Index(columnList = "PLACED_BY"),
                @Index(columnList = "SYMBOL"),
                @Index(columnList = "BUY_SELL"),
                @Index(columnList = "PENDING_QUANTITY"),
                @Index(columnList = "STATUS")
        }
)
public class TradeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PLACED_BY")
    private Long placedBy;

    @Column(name = "SYMBOL")
    @Enumerated(EnumType.STRING)
    private Symbol symbol;

    @Column(name = "BUY_SELL")
    @Enumerated(EnumType.STRING)
    private TradeOrderBuySell buySell;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private TradeOrderType type;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "QUANTITY")
    private BigDecimal quantity;

    @Column(name = "VALUE")
    private BigDecimal value;

    @Column(name = "TRADED_QUANTITY")
    private BigDecimal tradedQuantity;

    @Column(name = "PENDING_QUANTITY")
    private BigDecimal pendingQuantity;

    @Column(name = "DISCLOSED_QUANTITY")
    private BigDecimal disclosedQuantity;

    @Column(name = "VALIDITY")
    private Long validity;

    @Column(name = "TRIGGER_PRICE")
    private BigDecimal triggerPrice;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private TradeOrderStatus status;

    @Column(name = "REMARKS")
    private String remarks;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED", nullable = false, updatable = false)
    private Date created = new Date();

    @CreatedBy
    @Column(name = "CREATED_BY")
    private Long createdBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED", nullable = false)
    private Date updated = new Date();

    @LastModifiedBy
    @Column(name = "UPDATED_BY")
    private Long updatedBy;


    protected TradeOrder() {
    }

    public TradeOrder(Long placedBy, Symbol symbol, TradeOrderBuySell buySell, TradeOrderType type, BigDecimal quantity) {
        this.placedBy = placedBy;
        this.symbol = symbol;
        this.buySell = buySell;
        this.type = type;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlacedBy() {
        return placedBy;
    }

    public void setPlacedBy(Long placedBy) {
        this.placedBy = placedBy;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public TradeOrderBuySell getBuySell() {
        return buySell;
    }

    public void setBuySell(TradeOrderBuySell buySell) {
        this.buySell = buySell;
    }

    public TradeOrderType getType() {
        return type;
    }

    public void setType(TradeOrderType type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getTradedQuantity() {
        return tradedQuantity;
    }

    public void setTradedQuantity(BigDecimal tradedQuantity) {
        this.tradedQuantity = tradedQuantity;
    }

    public BigDecimal getPendingQuantity() {
        return pendingQuantity;
    }

    public void setPendingQuantity(BigDecimal pendingQuantity) {
        this.pendingQuantity = pendingQuantity;
    }

    public BigDecimal getDisclosedQuantity() {
        return disclosedQuantity;
    }

    public void setDisclosedQuantity(BigDecimal disclosedQuantity) {
        this.disclosedQuantity = disclosedQuantity;
    }

    public Long getValidity() {
        return validity;
    }

    public void setValidity(Long validity) {
        this.validity = validity;
    }

    public BigDecimal getTriggerPrice() {
        return triggerPrice;
    }

    public void setTriggerPrice(BigDecimal triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    public TradeOrderStatus getStatus() {
        return status;
    }

    public void setStatus(TradeOrderStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public enum TradeOrderBuySell {
        BUY, SELL;
    }

    public enum TradeOrderStatus {
        IN_BASKET("in-basket"), PLACED("placed"), PARTIALLY_FULFILLED("partially-fulfilled"), COMPLETELY_FULFILLED("completely-fulfilled"), REJECTED("rejected");
        private final String uriValue;

        TradeOrderStatus(String uriValue) {
            this.uriValue = uriValue;
        }

        public static TradeOrderStatus fromValue(String uriValue) {
            return valueOf(uriValue.replace("-", "_").toUpperCase());
        }

        public String uriValue() {
            return uriValue;
        }
    }

    public enum TradeOrderType {
        LIMIT("limit"), MARKET("market"), STOP_LOSS("stop-loss");
        private final String uriValue;

        TradeOrderType(String uriValue) {
            this.uriValue = uriValue;
        }

        public static TradeOrderType fromValue(String uriValue) {
            return valueOf(uriValue.replace("-", "_").toUpperCase());
        }

        public String uriValue() {
            return uriValue;
        }
    }

}
