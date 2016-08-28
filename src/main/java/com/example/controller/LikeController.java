package com.example.controller;

import com.example.async.EventModel;
import com.example.async.EventProducer;
import com.example.async.EventType;
import com.example.model.EntityType;
import com.example.model.HostHolder;
import com.example.service.LikeService;
import com.example.service.NewsService;
import com.example.util.DemoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
@Controller
public class LikeController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    LikeService likeService;

    @Autowired
    NewsService newsService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("newsId") int newsId ){
        int userId = hostHolder.getUser().getId();
        long likeCount = likeService.like(userId, EntityType.ENTITY_NEWS,newsId);
        newsService.updateLikeCount(newsId,(int)likeCount);

        eventProducer.fireEvent(new EventModel(EventType.LIKE).setActorId(hostHolder.getUser().getId()).setEntityId(newsId)
        .setEntityType(EntityType.ENTITY_NEWS).setEntityOwnerId(newsService.getById(newsId).getUserId()));

        return DemoUtils.getJsonString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("newsId") int newsId ){
        int userId = hostHolder.getUser().getId();
        long dislikeCount = likeService.dislike(userId, EntityType.ENTITY_NEWS,newsId);
        newsService.updateLikeCount(newsId,(int)dislikeCount);

        eventProducer.fireEvent(new EventModel(EventType.LIKE).setActorId(hostHolder.getUser().getId()).setEntityId(newsId)
                .setEntityType(EntityType.ENTITY_NEWS).setEntityOwnerId(newsService.getById(newsId).getUserId()));

        return DemoUtils.getJsonString(0,String.valueOf(dislikeCount));
    }

}
