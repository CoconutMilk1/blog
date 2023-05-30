package com.study.blog.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.entity.Article;
import com.study.blog.entity.Comment;
import com.study.blog.mapper.ArticleMapper;
import com.study.blog.mapper.CommentMapper;
import com.study.blog.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
