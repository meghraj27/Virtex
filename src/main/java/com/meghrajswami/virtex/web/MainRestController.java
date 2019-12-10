package com.meghrajswami.virtex.web;

import com.meghrajswami.virtex.domain.Depth;
import com.meghrajswami.virtex.domain.User;
import com.meghrajswami.virtex.repository.UserRepository;
import com.meghrajswami.virtex.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by megh on 7/29/2017.
 */
@RestController
public class MainRestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TradeService tradeService;

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public User login() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(((org.springframework.security.core.userdetails.User) principal).getUsername());
    }

    @RequestMapping(path = "/stats/depth", method = RequestMethod.GET)
    public ResponseEntity<Depth> getDepth() {
        return new ResponseEntity<Depth>(tradeService.getDepth(), HttpStatus.OK);
    }

    @RequestMapping(path = "/net_positions", method = RequestMethod.GET)
    public ResponseEntity<List<Object>> getNetpositions() {
        return new ResponseEntity<List<Object>>(tradeService.getNetPositions(1L), HttpStatus.OK);
    }
}
