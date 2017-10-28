package com.meghrajswami.bitex.web;

import com.meghrajswami.bitex.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Created by megh on 7/27/2017.
 */
@Controller
public class MainController {
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
    public String submit(@Valid @ModelAttribute("user") User user,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("user", user);
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
