package com.example.service;

import com.example.dao.CommentDao;
import com.example.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    public void addComment(Comment comment){
        commentDao.addComment(comment);
    }

    public int getCommentCount(int entityId,int entityType){
        return commentDao.getCommentCount(entityId,entityType);
    }

    public List<Comment> getCommentsByEntity(int entityId,int entityType){
        return commentDao.selectByEntity(entityId,entityType);
    }
}
