package com.study.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.blog.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
