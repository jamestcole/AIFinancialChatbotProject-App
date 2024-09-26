package com.sparta.financialadvisorchatbot.controllers;

import com.sparta.financialadvisorchatbot.service.FinancialAdvisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BasicWebController {

    @Autowired
    public FinancialAdvisorService financialAdvisorService;

    @GetMapping("/chatbot")
    public String getHome(Model model) {
        model.addAttribute("message", "Hello World!");
        return "home";
    }
}
