package ru.moore.lesson7.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloWorldController {

    @RequestMapping(value = "/hello")
    public String helloWorldController(@RequestParam(name = "name", required = false, defaultValue = "Мур")
                                                   String name, Model model) {model.addAttribute("name", name);
        return "hello";
    }
}
