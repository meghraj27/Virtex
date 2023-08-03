package com.meghrajswami.virtex.repository;

import com.meghrajswami.virtex.domain.TradeTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

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

//    @Query(nativeQuery = true, value = "select t1.symbol, avgBuyPrice, netBuyQty, avgSellPrice, netSellQty\n" +
//            "from\n" +
//            " (select tb.symbol, avg(tb.price) as avgBuyPrice, sum(tb.quantity) as netBuyQty\n" +
//            "  from trade_transaction tb where tb.bid_by_id=:userId \n" +
//            "  group by tb.symbol) t1,\n" +
//            " (select ts.symbol, avg(ts.price) as avgSellPrice, sum(ts.quantity) as netSellQty\n" +
//            "  from trade_transaction ts where ts.ask_by_id=:userId \n" +
//            "  group by ts.symbol) t2\n" +
//            "where t1.symbol=t2.symbol;")
//    Page<NetPosition> getNetPositions(@Param("userId") Long userId, Pageable pageable);

}
