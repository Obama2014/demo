package com.example.controller;

import com.example.service.UserService;
import com.example.util.DemoUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
@Controller
public class LoginController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping(path={"/register/"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String register(@RequestParam("username") String username,@RequestParam("password") String password,
                           @RequestParam(value="rememberme",defaultValue="0") int rememberme,HttpServletResponse response){
        try{
            Map<String,Object> map = userService.register(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if(rememberme>0){
                    cookie.setMaxAge(3600*24);
                }
                response.addCookie(cookie);
                return DemoUtils.getJsonString(0,"注册成功");
            }else{
                return DemoUtils.getJsonString(1,map);
            }
        }catch (Exception e){
            logger.error("\"注册异常\" + e.getMessage()");
            return DemoUtils.getJsonString(1,"注册异常");
        }

    }

    @RequestMapping(path={"/login/"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value="rememberme", defaultValue = "0") int rememberme,HttpServletResponse response){
        try{
            Map<String,Object> map = userService.login(username, password);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if(rememberme>0){
                    cookie.setMaxAge(3600*24);
                }
                response.addCookie(cookie);
                return DemoUtils.getJsonString(0,"登录成功");
            }else{
                return DemoUtils.getJsonString(1,map);
            }
        }catch (Exception e){
            logger.error("\"登录异常\" + e.getMessage()");
            return DemoUtils.getJsonString(1,"登录异常");
        }
    }

    @RequestMapping(path={"/logout/"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String logout(@CookieValue("ticket") String ticket){
        try{
            userService.logout(ticket);
            return DemoUtils.getJsonString(0,"登出成功");
        }catch (Exception e){
            logger.error("\"登出异常\" + e.getMessage()");
            return DemoUtils.getJsonString(1,"登出异常");
        }
    }
}
