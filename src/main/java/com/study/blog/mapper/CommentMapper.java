package com.study.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
