package com.study.blog.entity;


import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @TableName comment
 */
@Data
public class Comment implements Serializable {
    /**
     * 评论唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 回复评论标识
     */
    private Long lastId;
    /**
     * 评论内容标识
     */
    private String content;
    /**
     * 文章标识
     */
    private Long articleId;
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
     * 评论状态
     * 1:启用
     * 0:禁用
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer status;
    /**
     * 是否被采纳
     * 1:是采纳
     * 0:是未采纳
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer adopted;
}