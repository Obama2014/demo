package com.example.async;

import java.util.List;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public interface EventHandler {
    void doHandle(EventModel model);
    List<EventType> getSupportEventTypes();
}
