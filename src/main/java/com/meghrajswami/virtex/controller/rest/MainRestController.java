package com.meghrajswami.virtex.controller.rest;

import com.meghrajswami.virtex.domain.Depth;
import com.meghrajswami.virtex.domain.NetPosition;
import com.meghrajswami.virtex.domain.User;
import com.meghrajswami.virtex.domain.form.ChangePasswordForm;
import com.meghrajswami.virtex.exception.AccessDeniedException;
import com.meghrajswami.virtex.exception.ConfigurationException;
import com.meghrajswami.virtex.service.TradeService;
import com.meghrajswami.virtex.service.UserService;
import com.meghrajswami.virtex.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

/**
 * Created by megh on 7/29/2017.
 */
@RestController
public class MainRestController {

    @Autowired
    UserService userService;

    @Autowired
    TradeService tradeService;

    /**
     * Check if all the required fields are initialized properly.
     *
     * @throws ConfigurationException if any of the required field(s) is(are) not initialized properly.
     */
    @PostConstruct
    protected void checkConfiguration() {
        Helper.checkConfigNotNull(userService, "userService");
        Helper.checkConfigNotNull(tradeService, "tradeService");
    }

    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public User login() {
        return Helper.getAuthUser();
    }

    @RequestMapping(path = "/change-password", method = RequestMethod.POST)
    public void forgotPassword(@RequestBody @Valid ChangePasswordForm changePasswordForm,
                                 BindingResult result) throws AccessDeniedException {
        if (result.hasErrors()) {
            throw new IllegalArgumentException("Incorrect parameters: " + result.toString());
        }
        userService.changePassword(changePasswordForm);
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
