package com.example.model;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
