package com.meghrajswami.virtex.repository;

import com.meghrajswami.virtex.domain.TradeTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by megh on 5/7/2017.
 */
//@RepositoryRestResource(exported = false)
public interface TradeTransactionRepository extends PagingAndSortingRepository<TradeTransaction, Long> {

    TradeTransaction findFirstByOrderByCreatedDesc();

    @Query("select sum(t.quantity) from TradeTransaction t where t.bidById = :bidBy")
    BigDecimal totalBuyQuantity(@Param("bidBy") Long bidBy);

    @Query("select sum(t.quantity) from TradeTransaction t where t.askById = :askBy")
    BigDecimal totalSellQuantity(@Param("askBy") Long askBy);

    @Query(nativeQuery = true, value = "select t1.symbol, avg_buy_price, net_buy_qty, avg_sell_price, net_sell_qty\n" +
            "from\n" +
            " (select tb.symbol, avg(tb.price) as avg_buy_price, sum(tb.quantity) as net_buy_qty\n" +
            "  from bitex.trade_transaction tb where tb.bid_by_id=:userId\n" +
            "  group by tb.symbol) t1,\n" +
            " (select ts.symbol, avg(ts.price) as avg_sell_price, sum(ts.quantity) as net_sell_qty\n" +
            "  from bitex.trade_transaction ts where ts.ask_by_id=:userId\n" +
            "  group by ts.symbol) t2\n" +
            "where t1.symbol=t2.symbol;")
    List<Object> getNetPositions(@Param("userId") Long userId);
}
