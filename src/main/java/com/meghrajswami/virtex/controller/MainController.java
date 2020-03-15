package com.meghrajswami.virtex.controller;

import com.meghrajswami.virtex.domain.form.RegisterForm;
import com.meghrajswami.virtex.exception.ConfigurationException;
import com.meghrajswami.virtex.service.UserService;
import com.meghrajswami.virtex.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by megh on 7/27/2017.
 */
@Controller
public class MainController {


    @Autowired
    private UserService userService;

    /**
     * Check if all the required fields are initialized properly.
     *
     * @throws ConfigurationException if any of the required field(s) is(are) not initialized properly.
     */
    @PostConstruct
    protected void checkConfiguration() {
        Helper.checkConfigNotNull(userService, "userService");
    }

    //    @RequestMapping(value = "/{path:[^\\.]*}")
    //    public String redirect() {
    //        return "forward:/";
    //    }
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Principal principal) {
        return redirectCheck(principal, "login");
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Principal principal) {
        return redirectCheck(principal, "register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submit(@Valid RegisterForm registerForm,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        userService.createUser(registerForm);
        return "redirect:/login";
    }

    @RequestMapping(path = "/forgot-password", method = RequestMethod.GET)
    public String forgotPassword(Principal principal) {
        return redirectCheck(principal, "forgot_password");
    }

    @RequestMapping(path = "/")
    public String index() {
        return "landing";
    }

    @RequestMapping(path = "/dashboard", method = RequestMethod.GET)
    public String dashboard() {
        return "angular/index";
    }

    private String redirectCheck(Principal principal, String view) {
        if (principal != null) {
            String name = principal.getName();
            if (name != null && !name.isEmpty())
                return "redirect:/dashboard";
        }
        return view;
    }
}
