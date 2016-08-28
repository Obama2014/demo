package com.example.dao;


import com.example.model.News;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
@Mapper
public interface NewsDao {

    public static final String TABLE_NAME = " news ";
    public static final String INSERT_FIELDS = " title, link, image, like_count, comment_count, created_date, user_id ";
    public static final String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,")","values(#{title},#{link},#{image}," +
            "#{likeCount},#{commentCount},#{createdDate},#{userId})"})
    public void addNews(News news);

    @Select({"select ", SELECT_FIELDS , " from ", TABLE_NAME, " where id=#{id}"})
    public News getById(int id);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where user_id=#{userId} limit #{offset},#{limit}"})
    public List<News> selectByUserIdAndOffset(@Param("userId") int userId,@Param("offset") int offset,@Param("limit") int limit);

    @Update({"update",TABLE_NAME,"set comment_count = #{count} where id = #{id}"})
    public void updateCommentCount(@Param("count") int count,@Param("id") int id);

    @Update({"update ", TABLE_NAME, " set like_count = #{likeCount} where id=#{id}"})
    int updateLikeCount(@Param("id") int id, @Param("likeCount") int likeCount);
}
