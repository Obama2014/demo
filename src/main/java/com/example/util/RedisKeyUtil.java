package com.example.util;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String BIZ_LIKE = "LIKE";
    private static final String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT = "EVENT";

    public static String getLikeKey(int entityId,int entityType){
        return BIZ_LIKE+SPLIT+String.valueOf(entityType) + SPLIT + String .valueOf(entityId);
    }

    public static String getDisLikeKey(int entityId,int entityType){
        return BIZ_DISLIKE+SPLIT+String.valueOf(entityType) + SPLIT + String .valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return BIZ_EVENT;
    }

}
