package com.study.blog.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.entity.Article;
import com.study.blog.entity.Tag;
import com.study.blog.mapper.ArticleMapper;
import com.study.blog.mapper.TagMapper;
import com.study.blog.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
