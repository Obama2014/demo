package com.example;

import com.example.dao.UserDao;
import com.example.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testUserDao(){
        assert(userDao!=null);
        User user = new User();
        user.setName("Li");
        userDao.addUser(user);
    }
}
