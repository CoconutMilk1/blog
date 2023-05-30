package com.study.blog.dto;

import com.study.blog.entity.Comment;
import lombok.Data;

@Data
public class CommentDto extends Comment {
    /**
     * 作者
     */
    private String createUserName;

}
