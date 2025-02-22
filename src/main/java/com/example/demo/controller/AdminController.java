package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/admin")
    public String admin(){
        log.debug("className : {} ", this.getClass().getSimpleName());
        return "admin";
    }

}
