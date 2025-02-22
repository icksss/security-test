package com.example.demo.controller;

import com.example.demo.dto.JoinDTO;
import com.example.demo.exceptions.JikimException;
import com.example.demo.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final JoinService joinService;

    @GetMapping("join")
    public String joinP(){
        log.debug("className : {} joinP()", this.getClass().getSimpleName());
        return "join";
    }

    @PostMapping("joinProc")
    public String joinProcess(JoinDTO joinDTO){
        log.debug("joinDTO : {}" , joinDTO);
        log.debug("username : {} ", joinDTO.getUsername());
        try {
            joinService.joinProcess(joinDTO);
        }catch (JikimException je){
            je.printStackTrace();
            log.error(je.getMessage());
            return "join";
        }

        return "redirect:/login";
    }



}
