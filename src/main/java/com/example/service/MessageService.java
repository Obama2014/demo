package com.example.service;

import com.example.dao.MessageDao;
import com.example.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/8/20 0020.
 */
@Service
public class MessageService {

    @Autowired
    private MessageDao messageDao;

    public int addMessage(Message message) {
        return messageDao.addMessage(message);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        // conversation的总条数存在id里
        return messageDao.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getConversationList(int userId, int offset, int limit) {
        // conversation的总条数存在id里
        return messageDao.getConversationList(userId, offset, limit);
    }

    public int getUnreadCount(int userId, String conversationId) {
        return messageDao.getConversationUnReadCount(userId, conversationId);
    }
}
