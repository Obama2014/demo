package com.example;

import com.example.dao.LoginTicketDao;
import com.example.dao.NewsDao;
import com.example.dao.UserDao;
import com.example.model.LoginTicket;
import com.example.model.News;
import com.example.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2016/8/15 0015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
public class InitDatabaseTests {
    @Autowired
    UserDao userDao;

    @Autowired
    NewsDao newsDao;

    @Autowired
    LoginTicketDao loginTicketDao;

    @Test
    public void initData() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            //user.setId(i);
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword(String .format("password%d",i));
            user.setSalt("");
            userDao.addUser(user);

            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(i+1);
            news.setUserId(i);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
            newsDao.addNews(news);

           /* // 给每个资讯插入3个评论
            for(int j = 0; j < 3; ++j) {
                Comment comment = new Comment();
                comment.setUserId(i+1);
                comment.setCreatedDate(new Date());
                comment.setStatus(0);
                comment.setContent("这里是一个评论啊！" + String.valueOf(j));
                comment.setEntityId(news.getId());
                comment.setEntityType(EntityType.ENTITY_NEWS);
                commentDAO.addComment(comment);
            }*/


            LoginTicket ticket = new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i+1);
            ticket.setExpired(date);
            ticket.setTicket(String.format("TICKET%d", i+1));
            loginTicketDao.addTicket(ticket);

            loginTicketDao.updateStatus(ticket.getTicket(), 2);

        }

        Assert.assertEquals("newpassword", userDao.selectById(1).getPassword());
        userDao.deleteById(1);
        Assert.assertNull(userDao.selectById(1));

        Assert.assertEquals(1, loginTicketDao.selectByTicket("TICKET1").getUserId());
        //Assert.assertEquals(2, loginTicketDao.selectByTicket("TICKET1").getStatus());

        //Assert.assertNotNull(commentDAO.selectByEntity(1, EntityType.ENTITY_NEWS).get(0));
    }
}
