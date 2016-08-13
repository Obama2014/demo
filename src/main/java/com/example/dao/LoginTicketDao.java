package com.example.dao;

import com.example.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
@Mapper
public interface LoginTicketDao {
    public static final String TABLE_NAME = "login_ticket";
    public static final String INSERT_FIELDS = "user_id,ticket,expired,status";
    public static final String SELECT_FILEDS = "id," + INSERT_FIELDS;

    @Insert({"INSERT INTO ",TABLE_NAME,"(",INSERT_FIELDS,") VALUES(#{userId},#{ticket},#{expired},#{status})"})
    public void addTicket(LoginTicket ticket);


    @Update({"UPDATE",TABLE_NAME,"SET status=#{status} where ticket=#{ticket}"})
    public void updateStatus(@Param("ticket") String ticket,@Param("status") int status);

    @Select({"SELECT",SELECT_FILEDS,"FROM ",TABLE_NAME," WHERE ticket=#{ticket}"})
    public LoginTicket selectByTicket(@Param("ticket") String ticket);
}
