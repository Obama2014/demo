package com.example.async.handler;

import com.example.async.EventHandler;
import com.example.async.EventModel;
import com.example.async.EventType;
import com.example.model.Message;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
@Component
public class LoginExceptionHandler implements EventHandler{

    @Override
    public void doHandle(EventModel model) {
        //判断是否有异常登录
        Message message = new Message();
        message.setFromId(3);       //系统账号
        message.setToId(model.getActorId());
        message.setCreatedDate(new Date());
        message.setContent("你登录异常");
        message.setConversationId(message.getFromId() < message.getToId() ? String.format("%d_%d", message.getFromId(), message.getToId())
                :String.format("%d_%d", message.getToId(), message.getFromId()));

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
