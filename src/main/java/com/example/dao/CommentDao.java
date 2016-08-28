package com.example.dao;

import com.example.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
@Mapper
public interface CommentDao {
    public static final String TABLE_NAME = " comment ";
    public static final String INSERT_FIELDS = "content,created_date,entity_id,entity_type,user_id,status";
    public static final String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAME,"（",INSERT_FIELDS,") values(#{content},#{createdDate},#{entityId},#{entityType},#{userId},#{status})"})
    public void addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where entity_type=#{entityType} and entity_id=#{entityId} order by id desc "})
    List<Comment> selectByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from ", TABLE_NAME, " where entity_type=#{entityType} and entity_id=#{entityId}"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);
}
