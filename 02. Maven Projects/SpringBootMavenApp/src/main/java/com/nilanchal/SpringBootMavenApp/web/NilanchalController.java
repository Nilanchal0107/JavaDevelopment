package com.nilanchal.SpringBootMavenApp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nilanchal.SpringBootMavenApp.service.IGreetingService;

@Controller
public class NilanchalController
{
    @Autowired
    private IGreetingService service;

    @GetMapping("/greeting")
    public String generateWish1(Model model)
    {
        String res=service.generateGreeting();
        model.addAttribute("wish", res);

        return "greet";
    }
}
