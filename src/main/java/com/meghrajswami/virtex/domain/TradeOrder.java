package com.meghrajswami.virtex.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by Meghraj on 6/28/2016.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`TRADE_ORDER`",
        indexes = {
                @Index(columnList = "PLACED_BY"),
                @Index(columnList = "SYMBOL"),
                @Index(columnList = "SIDE"),
                @Index(columnList = "PENDING_QUANTITY"),
                @Index(columnList = "STATUS")
        }
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TradeOrder extends AuditableUserEntity {

    @Column(name = "PLACED_BY")
    private Long placedBy;

    @Column(name = "SYMBOL")
    @Enumerated(EnumType.STRING)
    private Symbol symbol;

    @Column(name = "SIDE")
    @Enumerated(EnumType.STRING)
    private Side side;

    @Enumerated(EnumType.STRING)
    private TradeOrderType type;

    @Column(precision = 19, scale = 4)
    private BigDecimal price;

    @Column(precision = 19, scale = 8)
    private BigDecimal quantity;

    @Column(precision = 19, scale = 4)
    private BigDecimal value;

    @Column(precision = 19, scale = 8)
    private BigDecimal tradedQuantity;

    @Column(name = "PENDING_QUANTITY", precision = 19, scale = 8)
    private BigDecimal pendingQuantity;

    @Column(precision = 19, scale = 8)
    private BigDecimal disclosedQuantity;

    private Long validity;

    @Column(precision = 19, scale = 4)
    private BigDecimal triggerPrice;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private TradeOrderStatus status;

    private String remarks;

    public TradeOrder(Long placedBy, Symbol symbol, Side side, TradeOrderType type, BigDecimal price, BigDecimal quantity) {
        this.placedBy = placedBy;
        this.symbol = symbol;
        this.side = side;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }

    public void trade(BigDecimal quantity) {
        pendingQuantity = pendingQuantity.subtract(quantity);
        if (tradedQuantity == null)
            tradedQuantity = quantity;
        else tradedQuantity = tradedQuantity.add(quantity);
    }

    public enum Side {
        BUY, SELL;
    }

    public enum TradeOrderStatus {
        IN_BASKET("in-basket"),
        PLACED("placed"),
        PARTIALLY_FULFILLED("partially-fulfilled"),
        COMPLETELY_FULFILLED("completely-fulfilled"),
        REJECTED("rejected"),
        EXPIRED("expired");

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
