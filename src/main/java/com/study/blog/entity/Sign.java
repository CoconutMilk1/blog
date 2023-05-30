package com.study.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName sign
 */
@Data
public class Sign implements Serializable {
    /**
     * 签到唯一标识
     */
    @TableId(type = IdType.AUTO)
    private long id;
    /**
     * 用户标识
     */
    private long userId;
    /**
     * 连续签到次数
     */
    private int duration;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
