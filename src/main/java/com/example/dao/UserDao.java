package com.example.dao;

import com.example.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
@Mapper
public interface UserDao {
    public static final String TABLE_NAME = " user ";
    public static final String INSERT_FIELDS = "name,password,salt,head_url";
    public static final String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")","values(#{name},#{password},#{salt},#{headUrl})"})
    public void addUser(User user);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where name = #{name}"})
    public User selectByName(String name);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id = #{id}"})
    public User selectById(int id);

    @Update({"update",TABLE_NAME,"set password = #{password} where id = #{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);

}
