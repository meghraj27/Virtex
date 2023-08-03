package com.meghrajswami.virtex.controller.rest;

import com.meghrajswami.virtex.domain.TradeOrder;
import com.meghrajswami.virtex.exception.ConfigurationException;
import com.meghrajswami.virtex.service.TradeService;
import com.meghrajswami.virtex.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by megh on 7/29/2017.
 */
@RestController
public class TradeOrderRestController {

    @Autowired
    TradeService tradeService;

    /**
     * Check if all the required fields are initialized properly.
     *
     * @throws ConfigurationException if any of the required field(s) is(are) not initialized properly.
     */
    @PostConstruct
    protected void checkConfiguration() {
        Helper.checkConfigNotNull(tradeService, "tradeService");
    }

    @RequestMapping(path = "/trade_orders/{id}", method = RequestMethod.GET)
    public ResponseEntity<TradeOrder> getTradeOrder(@Param("id") Long id) {
        return new ResponseEntity<TradeOrder>(tradeService.getOrder(id), HttpStatus.OK);
    }

    @RequestMapping(path = "/trade_orders", method = RequestMethod.GET)
    public ResponseEntity<Page<TradeOrder>> getTradeOrders(@Param("id") Long id, @PageableDefault Pageable pageable) throws IOException {
        return new ResponseEntity<Page<TradeOrder>>(tradeService.listOrders(pageable), HttpStatus.OK);
        // ImmutableList<Order> orders = Orders.collect(client.getEvents());
        // return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @RequestMapping(path = "/trade_orders", method = RequestMethod.POST)
    public ResponseEntity<TradeOrder> createTradeOrder(@RequestBody TradeOrder tradeOrder) throws Exception {
        return new ResponseEntity<TradeOrder>(tradeService.createOrder(tradeOrder), HttpStatus.OK);
    }

    @RequestMapping(path = "/trade_orders/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TradeOrder> updateTradeOrder(@Param("id") Long id, @RequestBody TradeOrder tradeOrder) {
        return new ResponseEntity<TradeOrder>(tradeService.updateOrder(id, tradeOrder), HttpStatus.OK);
    }

    @RequestMapping(path = "/trade_orders/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTradeOrder(@Param("id") Long id) {
        tradeService.deleteOrder(id);
        return new ResponseEntity<Boolean>(HttpStatus.OK);
    }

}
