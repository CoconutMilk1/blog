package com.study.blog.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.entity.Article;
import com.study.blog.entity.User;
import com.study.blog.mapper.ArticleMapper;
import com.study.blog.mapper.UserMapper;
import com.study.blog.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
