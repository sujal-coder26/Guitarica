package com.web.guitarapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ConsumeResponse")
public class ResponseController {
    @GetMapping("/get")
    public String get(Model model)
    {
        ConsumeJsonResponse data = new ConsumeJsonResponse();
        model.addAttribute("response", data.get().getBody());
        model.addAttribute("headers", data.get().getHeaders());

        return "output";
    }

}
