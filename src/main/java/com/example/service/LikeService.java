package com.example.service;

import com.example.util.JedisAdapter;
import com.example.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
@Service
public class LikeService {

    @Autowired
    JedisAdapter jedisAdapter;

    public int getLikeStatus(int userId,int entityType,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityId,entityType);
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId,entityType);
        if(jedisAdapter.sismember(likeKey,String.valueOf(userId))){
            return 1;
        }else if(jedisAdapter.sismember(dislikeKey,String.valueOf(userId))){
            return -1;
        }
        return 0;
    }

    public long like(int userId,int entityType,int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityId,entityType);
        jedisAdapter.sadd(likeKey,String.valueOf(userId));
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId,entityType);
        jedisAdapter.srem(dislikeKey,String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId,int entityType,int entityId){
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        jedisAdapter.sadd(dislikeKey,String.valueOf(userId));
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.srem(likeKey,String.valueOf(userId));
        return jedisAdapter.scard(dislikeKey);
    }
}
