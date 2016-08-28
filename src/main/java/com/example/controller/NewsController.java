package com.example.controller;

import com.example.model.*;
import com.example.service.CommentService;
import com.example.service.NewsService;
import com.example.service.QiniuService;
import com.example.service.UserService;
import com.example.util.DemoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    QiniuService qiniuService;

    @Autowired
    CommentService commentService;

    @RequestMapping(path={"/uploadImage/"},method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file){
        try{
           String imageUrl = newsService.saveImage(file);
           // String imageUrl = qiniuService.saveImage(file);
            if(imageUrl==null){

                return DemoUtils.getJsonString(1,"上传图片失败");
            }
            return DemoUtils.getJsonString(0,imageUrl);
        }catch(Exception e){
            logger.error("上传图片失败" + e.getMessage());
            return DemoUtils.getJsonString(1,"上传失败");
        }
    }

    @RequestMapping(path={"/image"},method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,HttpServletResponse response){
        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new File(DemoUtils.IMAGE_DIR+imageName)),response.getOutputStream());
        } catch (IOException e) {
            logger.error("读取图片错误" + imageName + e.getMessage());
        }
    }

    @RequestMapping(path={"/news/{newsId}"},method = {RequestMethod.GET})
    public String newsDetail(Model model,@PathVariable("newsId") int newsId){
        try{
            News news = newsService.getById(newsId);
            if(news!=null){
                List<Comment> comments = commentService.getCommentsByEntity(newsId, EntityType.ENTITY_NEWS);
                if(comments!=null&&!comments.isEmpty()){
                    List<ViewObject> vos = new ArrayList<>();
                    for(Comment comment:comments){
                        ViewObject vo = new ViewObject();
                        vo.set("comment",comment);
                        vo.set("user",userService.getUser(comment.getUserId()));
                        vos.add(vo);
                    }
                    model.addAttribute("comments",vos);
                }
            }
            model.addAttribute("news", news);
            model.addAttribute("owner",userService.getUser(news.getUserId()));
        }catch (Exception e){
            logger.error("获取资讯明细错误"+e.getMessage());
        }
        return "detail";
    }

    @RequestMapping(path={"/user/addNews/"},method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("title") String title,
                          @RequestParam("image") String image,
                          @RequestParam("link") String link){
        try{
            News news = new News();
            news.setCreatedDate(new Date());
            news.setTitle(title);
            news.setImage(image);
            news.setLink(link);
            if(hostHolder.getUser()!=null){
                news.setUserId(hostHolder.getUser().getId());
            }else{
                //设置匿名用户
                news.setUserId(3);
            }
            newsService.addNews(news);
            return DemoUtils.getJsonString(0,"发布成功！");
        }catch (Exception e){
            logger.error("添加资讯失败" + e.getMessage());
            return DemoUtils.getJsonString(1,"发布失败！");
        }
    }

    @RequestMapping(path={"/user/addComment"},method = {RequestMethod.POST})
    public String addComment(@RequestParam("content") String content,@RequestParam("newsId") int newsId){
        try{
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setCreatedDate(new Date());
            comment.setStatus(0);
            comment.setUserId(hostHolder.getUser().getId());
            commentService.addComment(comment);

            //更新评论数量
            int count = commentService.getCommentCount(newsId,EntityType.ENTITY_NEWS);
            newsService.updateCommentCount(count,newsId);
        }catch (Exception e){
            logger.error("提交评论错误"+e.getMessage());
        }
        return "redirect:/news/" + newsId;
    }

}
