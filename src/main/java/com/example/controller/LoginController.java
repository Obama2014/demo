package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
@Controller
public class LoginController {

    @RequestMapping(path={"/","/home"})
    @ResponseBody
    public String index(){
        return "Welcome!";
    }
}
