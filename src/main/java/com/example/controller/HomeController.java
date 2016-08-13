package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
@Controller
public class HomeController {

    @RequestMapping(path={"/","/home"},method = {RequestMethod.GET})
    public String index(){
        return "home";
    }

}
