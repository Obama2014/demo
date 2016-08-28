package com.example.service;

import com.example.dao.NewsDao;
import com.example.model.News;
import com.example.util.DemoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2016/8/16 0016.
 */
@Service
public class NewsService {
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Autowired
    NewsDao newsDao;

    public News getById(int id){
        return newsDao.getById(id);
    }

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDao.selectByUserIdAndOffset(userId,offset,limit);
    }

    public String saveImage(MultipartFile file) throws IOException{
        int dotPos = file.getOriginalFilename().lastIndexOf(".");
        if(dotPos<0){
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(dotPos+1);
        if(!DemoUtils.isFileAllowed(fileExt)){
            return null;
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-","") + "." + fileExt;

        file.transferTo(new File(DemoUtils.IMAGE_DIR+fileName));
        //Files.copy(file.getInputStream(), new File(DemoUtils.IMAGE_DIR + fileName).toPath(),
        //StandardCopyOption.REPLACE_EXISTING);

        return DemoUtils.DEMO_DOMAIN + "image?name=" + fileName;
    }

    public void addNews(News news){
        newsDao.addNews(news);
    }

    public void updateCommentCount(int count,int newsId){
        newsDao.updateCommentCount(count,newsId);
    }

    public int updateLikeCount(int id, int count) {
        return newsDao.updateLikeCount(id, count);
    }
}
