package com.meghrajswami.bitex.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Created by megh on 10/29/2017.
 */
public interface Depth {
    TradeTransaction getLastTrade();

    List<DepthItem> getBids();

    BigDecimal getBidQuantityTotal();

    List<DepthItem> getAsks();

    BigDecimal getAskQuantityTotal();

    /**
     * Provide current timestamp
     *
     * @return current timestamp
     */
    Long getLastChecked();

}