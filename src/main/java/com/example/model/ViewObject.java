package com.example.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
public class ViewObject {
    private Map<String,Object> map = new HashMap<>();

    public void set(String key,Object value){
        map.put(key,value);
    }

    public Object get(String key){
        return map.get(key);
    }

}
