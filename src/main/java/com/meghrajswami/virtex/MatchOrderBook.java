package com.meghrajswami.virtex;

import com.meghrajswami.virtex.domain.Symbol;
import com.meghrajswami.virtex.domain.TradeOrder;
import com.meghrajswami.virtex.domain.TradeTransaction;
import com.meghrajswami.virtex.domain.User;
import com.meghrajswami.virtex.repository.TradeOrderRepository;
import com.meghrajswami.virtex.repository.TradeTransactionRepository;
import com.meghrajswami.virtex.repository.UserRepository;
import com.paritytrading.foundation.ASCII;
import com.paritytrading.foundation.Longs;
import com.paritytrading.parity.match.OrderBook;
import com.paritytrading.parity.match.OrderBookListener;
import com.paritytrading.parity.match.Side;
import com.paritytrading.parity.util.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by megh on 12/4/2017.
 */
public class MatchOrderBook implements OrderBookListener {

    private static final Logger logger = LoggerFactory.getLogger(MatchOrderBook.class);

    private TradeOrderRepository tradeOrderRepository;
    private TradeTransactionRepository tradeTransactionRepository;
    private UserRepository userRepository;
    private OrderBook orderBook;
    private ParityConfig parityConfig;

    MatchOrderBook(ParityConfig parityConfig, TradeOrderRepository tradeOrderRepository,
                   TradeTransactionRepository tradeTransactionRepository,
                   UserRepository userRepository) {
        this.parityConfig = parityConfig;
        this.tradeOrderRepository = tradeOrderRepository;
        this.tradeTransactionRepository = tradeTransactionRepository;
        this.userRepository = userRepository;
        this.orderBook = new OrderBook(this);
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public void regenerateOrderBook() throws Exception {
        List<TradeOrder> tradeOrders = tradeOrderRepository.findPendingOrders();
        for (TradeOrder order : tradeOrders) {
            Side side = (order.getSide() == TradeOrder.Side.BUY) ? Side.BUY : Side.SELL;

            double quantity = order.getPendingQuantity().doubleValue();
            long instrument = ASCII.packLong(order.getSymbol().toString());
            double price = order.getPrice().doubleValue();

            Instrument config = parityConfig.getInstruments().get(instrument);
            if (config == null)
                throw new Exception("instrument " + order.getSymbol() + " don't exist");

            long sizeLong = (long) (quantity * config.getSizeFactor());
            long priceLong = (long) (price * config.getPriceFactor());

            orderBook.enter(order.getId(), side, priceLong, sizeLong);

            logger.info("Orderbook regenerated successfully");
        }
    }

    @Override
    public void match(long restingOrderId, long incomingOrderId, Side incomingSide, long price, long executedQuantity, long remainingQuantity) {
        logger.info("Order Matched");

        BigDecimal priceDecimal = new BigDecimal(price).divide(new BigDecimal(Longs.POWERS_OF_TEN[2]));
        BigDecimal executedQuantityDecimal = new BigDecimal(executedQuantity).divide(new BigDecimal(Longs.POWERS_OF_TEN[8]));
        BigDecimal value = priceDecimal.multiply(executedQuantityDecimal);

        TradeOrder restingOrder = tradeOrderRepository.findOne(restingOrderId);
        TradeOrder incomingOrder = tradeOrderRepository.findOne(incomingOrderId);

        Symbol symbol = incomingOrder.getSymbol();

        //update order book
        if (remainingQuantity == 0) {
            orderBook.cancel(restingOrderId, 0);

            restingOrder.setStatus(TradeOrder.TradeOrderStatus.COMPLETELY_FULFILLED);
        } else {
            restingOrder.setStatus(TradeOrder.TradeOrderStatus.PARTIALLY_FULFILLED);
        }

        switch (incomingOrder.getPendingQuantity().compareTo(executedQuantityDecimal)) {
            case 0:
                incomingOrder.setStatus(TradeOrder.TradeOrderStatus.COMPLETELY_FULFILLED);
                break;
            case 1:
                incomingOrder.setStatus(TradeOrder.TradeOrderStatus.PARTIALLY_FULFILLED);
                break;
            default:
                logger.error("Severe: pending quantity can't be less than executed quantity");
        }

        restingOrder.trade(executedQuantityDecimal);
        incomingOrder.trade(executedQuantityDecimal);

        //create trade in trade book
        TradeTransaction tradeTransaction = null;
        if (incomingSide == Side.BUY) {
            tradeTransaction = new TradeTransaction(incomingOrder.getPlacedBy(), incomingOrder.getId(),
                    restingOrder.getPlacedBy(), restingOrder.getId(),
                    restingOrder.getSymbol(), executedQuantityDecimal, priceDecimal);
        } else {
            tradeTransaction = new TradeTransaction(restingOrder.getPlacedBy(), restingOrder.getId(),
                    incomingOrder.getPlacedBy(), incomingOrder.getId(),
                    restingOrder.getSymbol(), executedQuantityDecimal, priceDecimal);
        }

        //update balances
        User restingOrderUser = userRepository.findOne(restingOrder.getPlacedBy());
        User incomingOrderUser = userRepository.findOne(incomingOrder.getPlacedBy());

        //balance and holdings adjustments
        //incoming BUY means market side is SELL
        if (incomingSide == Side.BUY) {
            incomingOrderUser.adjustMarginOnTradeBuy(symbol, priceDecimal, incomingOrder.getPrice(), executedQuantityDecimal);
            restingOrderUser.adjustMarginOnTradeSell(symbol, priceDecimal, executedQuantityDecimal);
        } else {
            incomingOrderUser.adjustMarginOnTradeSell(symbol, priceDecimal, executedQuantityDecimal);
            restingOrderUser.adjustMarginOnTradeBuy(symbol, priceDecimal, restingOrder.getPrice(), executedQuantityDecimal);
        }

        tradeTransactionRepository.save(tradeTransaction);
        tradeOrderRepository.save(restingOrder);
        tradeOrderRepository.save(incomingOrder);
        userRepository.save(incomingOrderUser);
        userRepository.save(restingOrderUser);

        logger.info("Match post processing completed successfully");
    }

    @Override
    public void add(long orderId, Side side, long price, long size) {
        logger.info("Order Added to the book");
    }

    /*
    all of the following events will trigger this function, use carefully :)
    removal of order from book after order completely matched,
    modification of order in between
    and cancellation of order
     */
    @Override
    public void cancel(long orderId, long canceledQuantity, long remainingQuantity) {
        logger.info("Order Cancelled from the Book");


    }

}
