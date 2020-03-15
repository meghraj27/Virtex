package com.meghrajswami.virtex.service;

import com.meghrajswami.virtex.domain.Depth;
import com.meghrajswami.virtex.domain.NetPosition;
import com.meghrajswami.virtex.domain.TradeOrder;
import com.meghrajswami.virtex.domain.TradeTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Meghraj.
 */
public interface TradeService {

    TradeOrder getOrder(Long id);

    Page<TradeOrder> listOrders(Pageable pageable);

    TradeOrder createOrder(TradeOrder tradeOrder) throws Exception;

    TradeOrder updateOrder(Long id, TradeOrder tradeOrder);

    boolean deleteOrder(Long id);

    TradeTransaction getTransaction(Long id);

    Page<TradeTransaction> listTransactions(Pageable pageable);

    TradeTransaction createTransaction(TradeTransaction tradeTransaction);

    TradeTransaction updateTransaction(Long id, TradeTransaction tradeTransaction);

    boolean deleteTransaction(Long id);

    Depth getDepth();

    Page<NetPosition> getNetPositions(Long userId, Pageable pageable);

}
