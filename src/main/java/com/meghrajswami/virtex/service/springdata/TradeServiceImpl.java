package com.meghrajswami.virtex.service.springdata;

import com.meghrajswami.virtex.ParityConfig;
import com.meghrajswami.virtex.domain.*;
import com.meghrajswami.virtex.repository.*;
import com.meghrajswami.virtex.service.TradeService;
import com.paritytrading.foundation.ASCII;
import com.paritytrading.parity.match.OrderBook;
import com.paritytrading.parity.match.Side;
import com.paritytrading.parity.util.Instrument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by megh on 10/14/2017.
 */
@Service
public class TradeServiceImpl implements TradeService {

    private Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

    //    @Autowired
    //    private Principal principal;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TradeOrderRepository tradeOrderRepository;

    @Autowired
    private TradeTransactionRepository tradeTransactionRepository;

    @Autowired
    private NetPositionRepository netPositionRepository;

    @Autowired
    private ParityConfig parityConfig;

    @Autowired
    private OrderBook orderBook;

    private User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(((org.springframework.security.core.userdetails.User) principal).getUsername());
    }

    public TradeOrder getOrder(Long id) {
        return tradeOrderRepository.findById(id).get();
    }

    public Page<TradeOrder> listOrders(Pageable pageable) {
        return tradeOrderRepository.findAll(pageable);
    }

    public TradeOrder createOrder(TradeOrder tradeOrder) throws Exception {
        User currentUser = getCurrentUser();
        tradeOrder.setPlacedBy(currentUser.getId());
        switch (tradeOrder.getSide()) {
            case BUY:
                return bid(currentUser, tradeOrder);
            case SELL:
                return ask(currentUser, tradeOrder);
            default:
                throw new Error("Illegal value for buySell");
        }
    }

    private TradeOrder bid(User currentUser, TradeOrder tradeOrder) throws Exception {
        //calculate tradeOrder.value using price*quantity
        BigDecimal value = tradeOrder.getPrice().multiply(tradeOrder.getQuantity());
        if (currentUser.getAvailableMargin() != null && currentUser.getAvailableMargin().compareTo(value) >= 0) {
            tradeOrder.setPendingQuantity(tradeOrder.getQuantity());
            tradeOrder.setStatus(TradeOrder.TradeOrderStatus.PLACED);

            // TODO: 12/3/2017 relying on exchange for now, must be fixed later
            currentUser.blockMargin(value);
            currentUser = userRepository.save(currentUser);
            TradeOrder order = tradeOrderRepository.save(tradeOrder);

            trade(order);

            return order;
        } else {
            tradeOrder.setStatus(TradeOrder.TradeOrderStatus.REJECTED);
            tradeOrder.setRemarks("Insufficiant margin availabile for trading");

            return tradeOrderRepository.save(tradeOrder);
        }
    }

    private TradeOrder ask(User currentUser, TradeOrder tradeOrder) throws Exception {
        //calculate tradeOrder.value using price*quantity
        // BigDecimal value = tradeOrder.getPrice().multiply(tradeOrder.getQuantity());
        if (haveBalanceCommodity(currentUser, tradeOrder.getSymbol(), tradeOrder.getQuantity())) {
            tradeOrder.setPendingQuantity(tradeOrder.getQuantity());
            tradeOrder.setStatus(TradeOrder.TradeOrderStatus.PLACED);

            // TODO: 12/3/2017 relying on exchange for now, must be fixed later
            //TODO: workaround, remove later
            if (currentUser.getHolding(tradeOrder.getSymbol()) == null) {
                currentUser.getHoldings().put(Symbol.BTC, new Holding(Symbol.BTC, new BigDecimal(3)));
                currentUser = userRepository.save(currentUser);
            }
            currentUser.getHolding(tradeOrder.getSymbol()).block(tradeOrder.getQuantity());
            currentUser = userRepository.save(currentUser);
            TradeOrder order = tradeOrderRepository.save(tradeOrder);

            trade(order);

            return order;
        } else {
            tradeOrder.setStatus(TradeOrder.TradeOrderStatus.REJECTED);
            tradeOrder.setRemarks("Insufficiant commodity availabile for trading");

            return tradeOrderRepository.save(tradeOrder);
        }
    }

    private void trade(TradeOrder order) throws Exception {
        Side side = (order.getSide() == TradeOrder.Side.BUY) ? Side.BUY : Side.SELL;

        // TODO: 12/6/2017 check once more when applying order modify/cancel feature
        //use pending quantity from here, because pending has been already set
        double quantity = order.getPendingQuantity().doubleValue();
        long instrument = ASCII.packLong(order.getSymbol().toString());
        double price = order.getPrice().doubleValue();

        Instrument config = parityConfig.getInstruments().get(instrument);
        if (config == null)
            throw new Exception("instrument " + order.getSymbol() + " don't exist");

        long sizeLong = (long) (quantity * config.getSizeFactor());
        long priceLong = (long) (price * config.getPriceFactor());

        orderBook.enter(order.getId(), side, priceLong, sizeLong);
    }

    private boolean haveBalanceCommodity(User currentUser, Symbol symbol, BigDecimal quantity) {
        Holding holding = currentUser.getHolding(symbol);
        return !(holding == null || holding.getAvailableQuantity().compareTo(BigDecimal.ZERO) <= 0);

        // BigDecimal totalBuyQuantity = tradeTransactionRepository.totalBuyQuantity(currentUser.getId());
        // BigDecimal totalSellQuantiry = tradeTransactionRepository.totalSellQuantiry(currentUser.getId());
        //
        // if (totalBuyQuantity == null) return false;
        //
        // BigDecimal balance = totalBuyQuantity.subtract(totalSellQuantiry);
        // return balance.compareTo(quantity) >= 0;

    }

    public TradeOrder updateOrder(Long id, TradeOrder tradeOrder) {
        return tradeOrderRepository.save(tradeOrder);
    }

    public boolean deleteOrder(Long id) {
        tradeOrderRepository.deleteById(id);
        return true;
    }

    public TradeTransaction getTransaction(Long id) {
        return tradeTransactionRepository.findById(id).get();
    }

    public Page<TradeTransaction> listTransactions(Pageable pageable) {
        return tradeTransactionRepository.findAll(pageable);
    }

    public TradeTransaction createTransaction(TradeTransaction tradeTransaction) {
        return tradeTransactionRepository.save(tradeTransaction);
    }

    public TradeTransaction updateTransaction(Long id, TradeTransaction tradeTransaction) {
        return tradeTransactionRepository.save(tradeTransaction);
    }

    public boolean deleteTransaction(Long id) {
        tradeTransactionRepository.deleteById(id);
        return true;
    }

    public Depth getDepth() {
        TradeTransaction trade = tradeTransactionRepository.findFirstByOrderByCreatedDesc();
        Page<DepthItem> bids = tradeOrderRepository.findBySide(TradeOrder.Side.BUY, PageRequest.of(0, 5, Sort.Direction.DESC, "price"));
        Page<DepthItem> asks = tradeOrderRepository.findBySide(TradeOrder.Side.SELL, PageRequest.of(0, 5, Sort.Direction.ASC, "price"));
        BigDecimal bidQuantityTotal = tradeOrderRepository.getBidQuantityTotal();
        BigDecimal askQuantityTotal = tradeOrderRepository.getAskQuantityTotal();

        return new Depth() {
            @Override
            public TradeTransaction getLastTrade() {
                return trade;
            }

            @Override
            public List<DepthItem> getBids() {
                return bids.getContent();
            }

            @Override
            public BigDecimal getBidQuantityTotal() {
                return bidQuantityTotal;
            }

            @Override
            public List<DepthItem> getAsks() {
                return asks.getContent();
            }

            @Override
            public BigDecimal getAskQuantityTotal() {
                return askQuantityTotal;
            }

            @Override
            public Long getLastChecked() {
                return System.currentTimeMillis();
            }
        };
    }

    public Page<NetPosition> getNetPositions(Long userId, Pageable pageable) {
        return netPositionRepository.findByUserId(userId, pageable);
    }

}
