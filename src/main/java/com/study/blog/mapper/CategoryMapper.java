package com.study.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.blog.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}
