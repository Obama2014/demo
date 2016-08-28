package com.example.async;

import com.alibaba.fastjson.JSONObject;
import com.example.util.JedisAdapter;
import com.example.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
@Service
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model){
        try{
            String json = JSONObject.toJSONString(model);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key,json);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
