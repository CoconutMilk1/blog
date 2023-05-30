package com.study.blog.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.entity.Sign;
import com.study.blog.mapper.SignMapper;
import com.study.blog.service.SignService;
import org.springframework.stereotype.Service;

@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign> implements SignService {
}
