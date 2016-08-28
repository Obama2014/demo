package com.example.controller;

import com.example.model.HostHolder;
import com.example.model.Message;
import com.example.model.User;
import com.example.model.ViewObject;
import com.example.service.MessageService;
import com.example.service.UserService;
import com.example.util.DemoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/20 0020.
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/msg/addMessage"}, method = { RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("fromId") int fromId,

                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        Message msg = new Message();
        msg.setContent(content);
        msg.setCreatedDate(new Date());
        msg.setToId(toId);
        msg.setFromId(fromId);
        msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) :
                String.format("%d_%d", toId, fromId));
        messageService.addMessage(msg);
        return DemoUtils.getJsonString(msg.getId(), "消息发送成功!");
    }

    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model, @RequestParam("conversationId") String conversationId) {
        try {
            List<ViewObject> messages = new ArrayList<>();
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                User user = userService.getUser(msg.getFromId());
                if (user == null) {
                    continue;
                }
                vo.set("headUrl", user.getHeadUrl());
                vo.set("userName", user.getName());
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
            return "letterDetail";
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String conversationList(Model model){
        try{
            User localUser = hostHolder.getUser();
            List<Message> messages = messageService.getConversationList(localUser.getId(),0,10);
            List<ViewObject> conversations = new ArrayList<>();
            for(Message message:messages){
                ViewObject vo = new ViewObject();
                vo.set("conversation",message);
                int targetUserId = localUser.getId()==message.getFromId()? message.getToId():message.getFromId();
                vo.set("user",userService.getUser(targetUserId));
                vo.set("unread",messageService.getUnreadCount(localUser.getId(),message.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations",conversations);
        }catch (Exception e){
            logger.error("获取消息列表失败!"+e.getMessage());
        }
        return "letter";
    }

}
