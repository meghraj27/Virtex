package com.meghrajswami.virtex.repository;

import com.meghrajswami.virtex.domain.Order;
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
