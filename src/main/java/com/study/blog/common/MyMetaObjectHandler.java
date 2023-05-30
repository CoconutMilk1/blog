package com.study.blog.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * MybatisPlus自动填充
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("新增数据填充...");
        //用户相关信息的自动填充
        this.strictInsertFill(metaObject, "name", String.class, "游客"+ UUID.randomUUID()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "avatar", String.class, "./uploads/avatar/avatar.png"); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "info", String.class, "快来填写你的个人介绍吧..."); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "points", Integer.class, 0); // 起始版本 3.3.0(推荐使用)
        //文章相关信息的自动填充
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "updateTime",LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "support", Integer.class, 0); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "oppose", Integer.class, 0); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "view", Integer.class, 0); // 起始版本 3.3.0(推荐使用)
        //评论相关信息的自动填充
        this.strictInsertFill(metaObject, "adopted", Integer.class, 0); // 起始版本 3.3.0(推荐使用)
        //状态自动填充
        this.strictInsertFill(metaObject, "status", Integer.class, 1); // 起始版本 3.3.0(推荐使用)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime",LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)

    }
}
