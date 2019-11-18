package com.meghrajswami.bitex.repository;

import com.meghrajswami.bitex.domain.DepthItem;
import com.meghrajswami.bitex.domain.TradeOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * Created by megh on 5/7/2017.
 */
//@RepositoryRestResource(exported = false)
public interface TradeOrderRepository extends PagingAndSortingRepository<TradeOrder, Long> {
    @Query("select t.price as price, t.pendingQuantity as quantity from TradeOrder t " +
            "where t.side = :side " +
            "and t.status in (:#{T(com.meghrajswami.bitex.domain.TradeOrder$TradeOrderStatus).PLACED}, :#{T(com.meghrajswami.bitex.domain.TradeOrder$TradeOrderStatus).PARTIALLY_FULFILLED}) "
    )
    Page<DepthItem> findBySide(@Param("side") TradeOrder.Side side, Pageable pageable);

    @Query("select sum(t.pendingQuantity) from TradeOrder t " +
            "where t.side = :#{T(com.meghrajswami.bitex.domain.TradeOrder$Side).BUY} " +
            "and t.status in (:#{T(com.meghrajswami.bitex.domain.TradeOrder$TradeOrderStatus).PLACED}, :#{T(com.meghrajswami.bitex.domain.TradeOrder$TradeOrderStatus).PARTIALLY_FULFILLED})")
    BigDecimal getBidQuantityTotal();

    @Query("select sum(t.pendingQuantity) from TradeOrder t " +
            "where t.side = :#{T(com.meghrajswami.bitex.domain.TradeOrder$Side).SELL} " +
            "and t.status in (:#{T(com.meghrajswami.bitex.domain.TradeOrder$TradeOrderStatus).PLACED}, :#{T(com.meghrajswami.bitex.domain.TradeOrder$TradeOrderStatus).PARTIALLY_FULFILLED})")
    BigDecimal getAskQuantityTotal();

    @Query("select t from TradeOrder t " +
            "where t.price >= :#{#order.price} " +
            "and t.side = :#{T(com.meghrajswami.bitex.domain.TradeOrder$Side).BUY} " +
            "and t.status in ('PLACED', 'PARTIALLY_FULFILLED') " +
            "order by t.price desc")
    Collection<TradeOrder> findQualifiedBids(@Param("order") TradeOrder order);

    @Query("select t from TradeOrder t " +
            "where t.price <= :#{#order.price} " +
            "and t.side = :#{T(com.meghrajswami.bitex.domain.TradeOrder$Side).SELL} " +
            "and t.status in ('PLACED', 'PARTIALLY_FULFILLED') " +
            "order by t.price asc")
    Collection<TradeOrder> findQualifiedAsks(@Param("order") TradeOrder order);

    @Query("select t from TradeOrder t " +
            "where t.status in ('PLACED', 'PARTIALLY_FULFILLED') " +
            "order by t.created asc")
    List<TradeOrder> findPendingOrders();
}
