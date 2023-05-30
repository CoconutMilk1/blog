package com.study.blog.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @TableName user
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    public User(){

    }
    public static User Register(String account,String password){
        User user = new User();
        user.setAccount(account);
        user.setName("游客");
        user.setPassword(password);
        user.setAvatar("./uploads/avatar/avatar.png");
        user.setStatus(1);
        user.setPoints(0);

        return user;
    }
    /**
     * 用户标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 账号名
     */
    private String account;
    /**
     * 用户名
     */
    @TableField(fill = FieldFill.INSERT)
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 介绍
     */
    @TableField(fill = FieldFill.INSERT)
    private String info;

    /**
     * 头像
     */
    @TableField(fill = FieldFill.INSERT)
    private String avatar;
    /**
     * 账号状态
     * 1:启用
     * 0:禁用
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer status;
    /**
     * 积分
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer points;

}