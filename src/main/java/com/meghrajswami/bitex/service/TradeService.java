package com.meghrajswami.bitex.service;

import com.meghrajswami.bitex.domain.TradeOrder;
import com.meghrajswami.bitex.domain.TradeTransaction;
import com.meghrajswami.bitex.repository.OrderRepository;
import com.meghrajswami.bitex.repository.TradeOrderRepository;
import com.meghrajswami.bitex.repository.TradeTransactionRepository;
import com.meghrajswami.bitex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by megh on 10/14/2017.
 */
@Service
public class TradeService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TradeOrderRepository tradeOrderRepository;

    @Autowired
    TradeTransactionRepository tradeTransactionRepository;

    public TradeOrder getTradeOrder(Long id) {
        return tradeOrderRepository.findOne(id);
    }

    public Page<TradeOrder> listTradeOrders(Pageable pageable) {
        return tradeOrderRepository.findAll(pageable);
    }

    public TradeOrder createTradeOrder(TradeOrder tradeOrder) {
        return tradeOrderRepository.save(tradeOrder);
    }

    public TradeOrder updateTradeOrder(Long id, TradeOrder tradeOrder) {
        return tradeOrderRepository.save(tradeOrder);
    }

    public boolean deleteTradeOrder(Long id) {
        tradeOrderRepository.delete(id);
        return true;
    }

    public TradeTransaction getTradeTransaction(Long id) {
        return tradeTransactionRepository.findOne(id);
    }

    public Page<TradeTransaction> listTradeTransactions(Pageable pageable) {
        return tradeTransactionRepository.findAll(pageable);
    }

    public TradeTransaction createTradeTransaction(TradeTransaction tradeTransaction) {
        return tradeTransactionRepository.save(tradeTransaction);
    }

    public TradeTransaction updateTradeTransaction(Long id, TradeTransaction tradeTransaction) {
        return tradeTransactionRepository.save(tradeTransaction);
    }

    public boolean deleteTradeTransaction(Long id) {
        tradeTransactionRepository.delete(id);
        return true;
    }

}
