package com.example.controller;

import com.example.model.HostHolder;
import com.example.model.News;
import com.example.model.ViewObject;
import com.example.service.NewsService;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/9 0009.
 */
@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path={"/","/home"},method = {RequestMethod.GET})
    public String index(Model model,@RequestParam(value="pop",defaultValue = "0") int pop){
        List<ViewObject> vos = getNews(0,0,10);
        if(hostHolder.getUser()!=null){
            pop = 0;
        }
        model.addAttribute("pop",pop);
        model.addAttribute("vos",vos);
        logger.info("HomeController.index() executed");
        return "home";
    }

    @RequestMapping(path={"/user/{userId}"},method = {RequestMethod.GET})
    public String userIndex(Model model,@PathVariable("userId") int userId){
        model.addAttribute("vos",getNews(userId,0,10));
        return "home";
    }

    private List<ViewObject> getNews(int userId,int offset,int limit){
        List<News> newsList = newsService.getLatestNews(userId,offset,limit);

        List<ViewObject> vos = new ArrayList<>();
        for(News news:newsList){
            ViewObject vo = new ViewObject();
            vo.set("news",news);
            vo.set("user",userService.getUser(news.getUserId()));

            vos.add(vo);
        }
        return vos;
    }
}
