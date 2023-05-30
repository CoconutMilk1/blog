package com.study.blog.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.entity.Article;
import com.study.blog.entity.Category;
import com.study.blog.mapper.ArticleMapper;
import com.study.blog.mapper.CategoryMapper;
import com.study.blog.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
