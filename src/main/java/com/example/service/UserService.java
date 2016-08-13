package com.example.service;

import com.example.dao.LoginTicketDao;
import com.example.dao.UserDao;
import com.example.model.LoginTicket;
import com.example.model.User;
import com.example.util.DemoUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    public Map<String,Object> register(String username,String password){

        //初步对username和password进行验证
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msgname","用户名不能为空");
        }
        if(StringUtils.isBlank(password)){
            map.put("msgpwd","密码不能为空");
        }
        if(userDao.selectByName(username)!=null){
            map.put("msgname","用户名已经存在");
            return map;
        }

        //注册
        User user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(DemoUtils.MD5(password+user.getSalt()));
        userDao.addUser(user);

        //直接登录
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public Map<String,Object> login(String username,String password){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msgname","用户名不能为空");
        }
        if(StringUtils.isBlank(password)){
            map.put("msgpwd","密码不能为空");
        }
        User user = userDao.selectByName(username);
        if(user==null){
            map.put("msgname","用户名不存在");
            return map;
        }
        String salt = user.getSalt();
        if(!DemoUtils.MD5(password+salt).equals(user.getPassword())){
            map.put("msgpwd","密码错误");
            return map;
        }

        //分派LoginTicket(Token)
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        ticket.setStatus(0);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24); //有效时间为一天
        ticket.setExpired(date);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket){
        loginTicketDao.updateStatus(ticket,1);
    }
}
