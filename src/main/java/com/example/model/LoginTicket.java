package com.example.model;

import java.util.Date;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    private int status; //0有效，1无效
    private String ticket;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }



}
