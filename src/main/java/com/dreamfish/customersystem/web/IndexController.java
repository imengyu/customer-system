package com.dreamfish.customersystem.web;

import com.dreamfish.customersystem.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping(value = "/", name = "Index")
    public String index() {
        return "hello";
    }
}
