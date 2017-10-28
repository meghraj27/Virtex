package com.meghrajswami.bitex.repository;

import com.meghrajswami.bitex.domain.TradeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by megh on 5/7/2017.
 */
//@RepositoryRestResource(exported = false)
public interface TradeOrderRepository extends PagingAndSortingRepository<TradeOrder, Long> {

}
