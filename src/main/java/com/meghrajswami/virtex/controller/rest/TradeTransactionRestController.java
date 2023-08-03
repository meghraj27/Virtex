package com.meghrajswami.virtex.controller.rest;

import com.meghrajswami.virtex.domain.TradeTransaction;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * Created by megh on 7/29/2017.
 */
@RestController
public class TradeTransactionRestController {

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

    @RequestMapping(path = "/trade_transactions/{id}", method = RequestMethod.GET)
    public ResponseEntity<TradeTransaction> getTradeTransaction(@Param("id") Long id) {
        return new ResponseEntity<TradeTransaction>(tradeService.getTransaction(id), HttpStatus.OK);
    }

    @RequestMapping(path = "/trade_transactions", method = RequestMethod.GET)
    public ResponseEntity<Page<TradeTransaction>> getTradeTransactions(@Param("id") Long id, @PageableDefault Pageable pageable) {
        return new ResponseEntity<Page<TradeTransaction>>(tradeService.listTransactions(pageable), HttpStatus.OK);
    }

    //    @RequestMapping(path = "/trade_transactions", method = RequestMethod.POST)
    //    public ResponseEntity<TradeTransaction> createTradeTransaction(@RequestBody TradeTransaction tradeTransaction) {
    //        return new ResponseEntity<TradeTransaction>(tradeService.createTradeTransaction(tradeTransaction), HttpStatus.OK);
    //    }

    //    @RequestMapping(path = "/trade_transactions/{id}", method = RequestMethod.PUT)
    //    public ResponseEntity<TradeTransaction> updateTradeTransaction(@Param("id") Long id, @RequestBody TradeTransaction tradeTransaction) {
    //        return new ResponseEntity<TradeTransaction>(tradeService.updateTradeTransaction(id, tradeTransaction), HttpStatus.OK);
    //    }

    //    @RequestMapping(path = "/trade_transactions/{id}", method = RequestMethod.DELETE)
    //    public ResponseEntity<Boolean> deleteTradeTransaction(@Param("id") Long id) {
    //        tradeService.deleteTradeTransaction(id);
    //        return new ResponseEntity<Boolean>(HttpStatus.OK);
    //    }

}
