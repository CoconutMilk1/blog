package com.study.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.study.blog.common.R;
import com.study.blog.entity.Sign;
import com.study.blog.entity.User;
import com.study.blog.service.SignService;
import com.study.blog.service.UserService;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/sign")
@Slf4j
public class SignController {
    @Resource
    private SignService signService;
    @Resource
    private UserService userService;

    /**
     * 用户签到
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    @Transactional
    public R<String> sign(@PathVariable Integer id) {
        //1、根据页面传入id查询数据库的签到信息
        LambdaQueryWrapper<Sign> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Sign::getUserId, id);
        Sign sign = signService.getOne(lambdaQueryWrapper);
        //2、如果没有查询到结果,则签到系统出现异常
        if (sign == null) {
            log.info("签到系统出现异常");
            return R.error("签到系统出现异常...");
        }
        //3、获取用户上次签到时间和连续签到时间,以及用户的积分信息
        LocalDateTime updateTime = sign.getUpdateTime();
        int duration = sign.getDuration();
        User user = userService.getById(id);
        Integer points = user.getPoints();
        //4、将上次签到时间和当前时间进行比对
        updateTime = updateTime.withMinute(0).withHour(0).withSecond(0);//将小时,分钟,秒钟清空,实现每天签到
        long days = updateTime.until(LocalDateTime.now(), ChronoUnit.DAYS);
        //5、用户签到判断,以及数据的更新
        if (days == 0) {
            //用户当天已经签到
            return R.error("今天已经签过到了,请明天再来");
        } else if (days == 1) {
            //用户连续签到
            if (duration > 7) {
                //连续签到七天以上,积分额外加2
                points += 2;
            }
            //记录连续签到天数
            duration += 1;
        } else {
            //用户签到中断,重新开始计算连续签到
            duration = 1;
        }
        user.setPoints(points + 3);
        sign.setUpdateTime(LocalDateTime.now());
        sign.setDuration(duration);
        //6、对数据库进行数据更新
        signService.update(sign, lambdaQueryWrapper);
        userService.updateById(user);
        return R.success("今天是签到的第" + duration + "天");
    }
}
