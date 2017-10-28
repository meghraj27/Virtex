package com.meghrajswami.bitex.repository;

import com.meghrajswami.bitex.domain.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by megh on 5/7/2017.
 */
//@RepositoryRestResource(exported = false)
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    List<Order> findByUserId(String userId);

    List<Order> findByPaymentRefId(String paymentRefId);
}
