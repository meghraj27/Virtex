package com.meghrajswami.bitex.repository;

import com.meghrajswami.bitex.domain.TradeTransaction;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by megh on 5/7/2017.
 */
//@RepositoryRestResource(exported = false)
public interface TradeTransactionRepository extends PagingAndSortingRepository<TradeTransaction, Long> {

}
