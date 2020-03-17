package com.meghrajswami.virtex.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
import java.math.BigDecimal;

/**
 * Created by megh on 6/28/2016.
 */
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
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TradeTransaction extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bidById;

    private Long bidTradeOrderId;

    private Long askById;

    private Long askTradeOrderId;

    @Enumerated(EnumType.STRING)
    private Symbol symbol;

    @Column(precision = 19, scale = 8)
    private BigDecimal quantity;

    @Column(precision = 19, scale = 4)
    private BigDecimal price;

    @Column(precision = 19, scale = 4)
    private BigDecimal value;

    public TradeTransaction(Long bidById,
                            Long bidTradeOrderId,
                            Long askById,
                            Long askTradeOrderId,
                            Symbol symbol,
                            BigDecimal quantity,
                            BigDecimal price) {
        this.bidById = bidById;
        this.bidTradeOrderId = bidTradeOrderId;
        this.askById = askById;
        this.askTradeOrderId = askTradeOrderId;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.value = price.multiply(quantity);
    }

}
