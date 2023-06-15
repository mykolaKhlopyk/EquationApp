package com.mkh.equationapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StarterController {
    @GetMapping
    public String getIndexPage(){
        return "redirect:/equation/index";
    }
}
