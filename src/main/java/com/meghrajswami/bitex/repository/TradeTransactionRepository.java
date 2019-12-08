package com.meghrajswami.bitex.repository;

import com.meghrajswami.bitex.domain.TradeTransaction;
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
    BigDecimal totalSellQuantiry(@Param("askBy") Long askBy);
}
