package com.study.blog.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @TableName tag
 */
@Data
public class Tag implements Serializable {
    /**
     * 标签唯一标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 文章唯一标识
     */
    private Long articleId;
    /**
     * 标签内容
     */
    private String value;

}