package com.study.blog.entity;


import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @TableName article
 */
@Data
public class Article implements Serializable {

    /**
     * 唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 预览图
     */
    private String firstPicture;
    /**
     * 内容
     */
    private String content;
    /**
     * 分类标识
     */
    private Long categoryId;
    /**
     * 用户标识
     */
    private Long createUserId;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 点赞
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer support;
    /**
     * 踩
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer oppose;
    /**
     * 浏览量
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer view;
    /**
     * 悬赏
     */
    private Integer reward;
    /**
     * 文章状态
     * 1:启用
     * 0:禁用
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer status;
}