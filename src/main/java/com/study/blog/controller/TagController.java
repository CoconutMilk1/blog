package com.study.blog.controller;

import com.study.blog.service.TagService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能未开发
 */
@RestController
@RequestMapping("tag")
public class TagController {
    @Resource
    private TagService tagService;

}
