package com.socket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lsc
 * <p> </p>
 */
@Controller
public class IndexController {

    @GetMapping("index")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("socket");
        return modelAndView;
    }
}
