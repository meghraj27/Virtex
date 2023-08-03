package com.meghrajswami.virtex.domain;

import lombok.Getter;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by Meghraj.
 */
@Entity
@Immutable
@Table(name = "`NET_POSITION`")
@Getter
public class NetPosition {

    @Id
    Long userId;

    String symbol;

    BigDecimal avgBuyPrice;

    BigDecimal netBuyQty;

    BigDecimal avgSellPrice;

    BigDecimal netSellQty;
}
