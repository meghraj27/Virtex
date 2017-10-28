package com.meghrajswami.bitex.web;

import com.meghrajswami.bitex.domain.TradeOrder;
import com.meghrajswami.bitex.service.TradeService;
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

/**
 * Created by megh on 7/29/2017.
 */
@RestController
public class TradeOrderRestController {

    @Autowired
    TradeService tradeService;

    @RequestMapping(path = "/trade_orders/{id}", method = RequestMethod.GET)
    public ResponseEntity<TradeOrder> getTradeOrder(@Param("id") Long id) {
        return new ResponseEntity<TradeOrder>(tradeService.getTradeOrder(id), HttpStatus.OK);
    }

    @RequestMapping(path = "/trade_orders", method = RequestMethod.GET)
    public ResponseEntity<Page<TradeOrder>> getTradeOrders(@Param("id") Long id, @PageableDefault Pageable pageable) {
        return new ResponseEntity<Page<TradeOrder>>(tradeService.listTradeOrders(pageable), HttpStatus.OK);
    }

    @RequestMapping(path = "/trade_orders", method = RequestMethod.POST)
    public ResponseEntity<TradeOrder> createTradeOrder(@RequestBody TradeOrder tradeOrder) {
        return new ResponseEntity<TradeOrder>(tradeService.createTradeOrder(tradeOrder), HttpStatus.OK);
    }

    @RequestMapping(path = "/trade_orders/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TradeOrder> updateTradeOrder(@Param("id") Long id, @RequestBody TradeOrder tradeOrder) {
        return new ResponseEntity<TradeOrder>(tradeService.updateTradeOrder(id, tradeOrder), HttpStatus.OK);
    }

    @RequestMapping(path = "/trade_orders/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTradeOrder(@Param("id") Long id) {
        tradeService.deleteTradeOrder(id);
        return new ResponseEntity<Boolean>(HttpStatus.OK);
    }

}
