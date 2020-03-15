package com.meghrajswami.virtex.controller.rest;

import com.meghrajswami.virtex.domain.Depth;
import com.meghrajswami.virtex.domain.NetPosition;
import com.meghrajswami.virtex.domain.User;
import com.meghrajswami.virtex.exception.ConfigurationException;
import com.meghrajswami.virtex.repository.UserRepository;
import com.meghrajswami.virtex.service.TradeService;
import com.meghrajswami.virtex.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
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

    /**
     * Check if all the required fields are initialized properly.
     *
     * @throws ConfigurationException if any of the required field(s) is(are) not initialized properly.
     */
    @PostConstruct
    protected void checkConfiguration() {
        Helper.checkConfigNotNull(userRepository, "userRepository");
        Helper.checkConfigNotNull(tradeService, "tradeService");
    }

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public User login() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(((org.springframework.security.core.userdetails.User) principal).getUsername());
    }

    @RequestMapping(path = "/stats/depth", method = RequestMethod.GET)
    public ResponseEntity<Depth> getDepth() {
        return new ResponseEntity<>(tradeService.getDepth(), HttpStatus.OK);
    }

    @RequestMapping(path = "/net_positions", method = RequestMethod.GET)
    public ResponseEntity<Page<NetPosition>> getNetpositions(@PageableDefault Pageable pageable) {
        Long currentUserId = Helper.getAuthUser().getId();
        return new ResponseEntity<>(tradeService.getNetPositions(currentUserId, pageable), HttpStatus.OK);
    }
}
