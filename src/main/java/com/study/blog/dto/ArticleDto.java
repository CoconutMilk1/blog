package com.study.blog.dto;

import com.study.blog.entity.Article;
import com.study.blog.entity.Category;
import com.study.blog.entity.Tag;
import com.study.blog.entity.User;
import com.study.blog.service.CategoryService;
import com.study.blog.service.UserService;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleDto extends Article {
    /**
     * 文章对应的标签
     */
    private List<Tag> tags = new ArrayList<>();
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 作者
     */
    private String createUserName;
    
}
