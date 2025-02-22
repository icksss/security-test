package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Iterator;

@Controller
public class MainController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String mainP(Model model){
        log.debug("className : {} ", this.getClass().getName());
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth =  context.getAuthentication();

        String id = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> it = authorities.iterator();
        GrantedAuthority au = it.next();
        String role = au.getAuthority();

        model.addAttribute("id",id);
        model.addAttribute("role",role);
        return "main";
    }
}
