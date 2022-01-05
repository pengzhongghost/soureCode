package com.手写springmvc.controller;

import com.手写springmvc.RequestMapping;
import com.手写springmvc.RequestParam;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

    @RequestMapping("/test")
    public String test(@RequestParam("name") String name) {
        System.out.println("------RequestMapping:" + name);
        return "test";
    }

    public String test01() {
        return "";
    }

}
