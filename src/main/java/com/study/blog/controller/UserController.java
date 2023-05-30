package com.study.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.study.blog.common.R;
import com.study.blog.entity.User;
import com.study.blog.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 相关用户的控制器
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/login")
    private R<User> login(@RequestBody User user, HttpServletRequest request) {
        //1、将页面提交的密码password进行md5加密处理
        String password = user.getPassword();
        String autoLoginPassword = password;
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2、根据页面提交的账号account查询数据库
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getAccount,user.getAccount());
        user = userService.getOne(lambdaQueryWrapper);
        //3、如果没有查询到则返回登录失败结果
        if (user == null){
            log.info(user.getAccount()+"查询为null");
            return R.error("登录失败");
        }
        //4、密码比对，如果不一致则返回登录失败结果
        if(!user.getPassword().equals(password)){
            log.info(user.getAccount()+"登录失败");
            return R.error("登录失败");
        }
        //5、查看用户状态，如果为已禁用状态，则返回员工已禁用结果
        if(user.getStatus() == 0){
            log.info(user.getAccount()+"已禁用");
            return R.error("账号已禁用");
        }
        log.info(user.getAccount()+"登录成功");
        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("user",user.getId());
        //7、设置自动登录的未加密密码.
        user.setPassword(autoLoginPassword);
        return R.success(user);
    }

    /**
     * 用户注册
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/register")
    private R<User> register(@RequestBody User user,HttpServletRequest request){
        //1、将页面提交的密码password进行md5加密处理
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2、校验数据库中是否已经存在该账号名
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(User::getAccount,user.getAccount());
        User dbUser = userService.getOne(lambdaQueryWrapper);
        //3、如果账号名已存在,注册失败
        if (dbUser != null){
            log.info(user.getAccount()+"账号已存在");
            return R.error("账号已存在");
        }
        //4、添加用户
        user.setPassword(password);
        userService.save(user);
        //5、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("user",user.getId());
        return R.success(user);
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前登录用户的id
        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }

    /**
     * 用户积分展示
     * @param id
     * @return
     */
    @GetMapping("/points")
    public R<Integer> getPoints(Integer id){
        User user = userService.getById(id);
        if (user == null){
            R.error("用户积分获取异常");
        }
        Integer points = user.getPoints();
        return R.success(points);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PutMapping()
    public R<String> update(@RequestBody User user){
        //1、将页面提交的密码password进行md5加密处理
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2.判断用户是否存在
        Long userId = user.getId();
        User oldUser = userService.getById(userId);
        if (oldUser==null){
            return R.error("用户修改失败!");
        }
        //3.更新数据
        user.setPassword(password);
        userService.updateById(user);
        return R.success("修改个人信息成功.");
    }
}
