package com.study.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.blog.common.R;
import com.study.blog.dto.ArticleDto;
import com.study.blog.entity.Article;
import com.study.blog.entity.Category;
import com.study.blog.entity.User;
import com.study.blog.service.ArticleService;
import com.study.blog.service.CategoryService;
import com.study.blog.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 相关文章的控制器
 */
@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {
    @Resource
    private ArticleService articleService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private UserService userService;
    /*
     * 对象拷贝
     * 参数一:拷贝来源
     * 参数二:拷贝目标
     * 参数三:忽略属性
     * BeanUtils.copyProperties(pageInfo, articleDtoPage, "records");
     * */
    /**
     * 文章根据标题分页查询
     * @param page
     * @param pageSize
     * @param title
     * @return
     */
    @GetMapping("/title")
    public R<Page> pageByTitle(int page, int pageSize, String title) {
        //构造分页构造器对象
        Page<Article> pageInfo = new Page<>(page, pageSize);
        Page<ArticleDto> articleDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件(title不为空时才添加这个条件)
        queryWrapper.like(title != null, Article::getTitle, title);
        //添加排序条件(通过悬赏额进行排序)
        queryWrapper.orderByDesc(Article::getReward);
        //执行分页查询
        articleService.page(pageInfo, queryWrapper);
        //获取文章集合
        List<Article> records = pageInfo.getRecords();
        //对article进行处理,并将对应数据查询及存储到数据传输模型中
        List<ArticleDto> list = records
                .stream()
                .map(this::getArticleDto)
                .collect(Collectors.toList());
        articleDtoPage.setRecords(list);
        return R.success(articleDtoPage);
    }

    /**
     * 文章根据用户分页查询
     * @param page
     * @param pageSize
     * @param uid
     * @return
     */
    @GetMapping("/uid")
    public R<Page> pageByUid(int page, int pageSize, Integer uid) {
        //构造分页构造器对象
        Page<Article> pageInfo = new Page<>(page, pageSize);
        Page<ArticleDto> articleDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件(文章作者id等于传过来的id)
        queryWrapper.eq(Article::getCreateUserId,uid);
        //添加排序条件(通过悬赏额进行排序)
        queryWrapper.orderByDesc(Article::getReward);
        //执行分页查询
        articleService.page(pageInfo, queryWrapper);
        //获取文章集合
        List<Article> records = pageInfo.getRecords();
        //对article进行处理,并将对应数据查询及存储到数据传输模型中
        List<ArticleDto> list = records
                .stream()
                .map(this::getArticleDto)
                .collect(Collectors.toList());
        articleDtoPage.setRecords(list);
        return R.success(articleDtoPage);
    }

    /**
     * 文章根据文章id查询
     * @param aid
     * @return
     */
    @GetMapping("/aid")
    public R<ArticleDto> showArticle(Integer aid) {
        //1.根据文章id获取文章信息
        Article article = articleService.getById(aid);
        //2.判断文章是否为空
        if (article==null) {
            return R.error("不存在该文章");
        }
        //3.将数据封装进入articleDto
        ArticleDto articleDto = getArticleDto(article);
        return R.success(articleDto);
    }

    /**
     * 发布文章
     * @param article
     * @return
     */
    @PostMapping("/save")
    @Transactional
    public R<String> save(@RequestBody Article article){
        //由于功能未完全开发,这里需要对未完成的功能进行数据的默认初始化
        if (article.getFirstPicture()==null){
            article.setFirstPicture("uploads/image/de8f9d9ad5f64f1c9a403bd8e8963f9c.png");
        }
        if (article.getCategoryId()==null){
            article.setCategoryId(1L);
        }
        //1.校验用户积分是否满足提供悬赏
        User user = userService.getById(article.getCreateUserId());
        Integer points = user.getPoints();
        Integer reward = article.getReward();
        if(points < reward){
            return R.error("用户积分不足");
        }
        //2.积分满足,扣除用户积分,并发布文章
        user.setPoints(points-reward);
        userService.updateById(user);
        articleService.save(article);
        return R.success("发布文章成功");
    }
    @GetMapping("/orderByTime")
    public R<Page> pageByUidOrderByTime(int page,int pageSize,Integer uid){
        //构造分页构造器对象
        Page<Article> pageInfo = new Page<>(page, pageSize);
        Page<ArticleDto> articleDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件(文章作者id等于传过来的id)
        queryWrapper.eq(Article::getCreateUserId,uid);
        //添加排序条件(通过悬赏额进行排序)
        queryWrapper.orderByDesc(Article::getCreateTime);
        //执行分页查询
        articleService.page(pageInfo, queryWrapper);
        //获取文章集合
        List<Article> records = pageInfo.getRecords();
        //对article进行处理,并将对应数据查询及存储到数据传输模型中
        List<ArticleDto> list = records
                .stream()
                .map(this::getArticleDto)
                .collect(Collectors.toList());
        articleDtoPage.setRecords(list);
        return R.success(articleDtoPage);
    }
    /**
     * 用于封装ArticleDto的方法
     * @param article
     * @return
     */
    private ArticleDto getArticleDto(Article article) {
        //1.创建articleDto对象
        ArticleDto articleDto = new ArticleDto();
        //2.将article存入articleDto
        BeanUtils.copyProperties(article, articleDto);
        //3.从article中获取分类
        Long categoryId = article.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            String categoryName = category.getName();
            articleDto.setCategoryName(categoryName);
        }
        //4.从article中获取用户
        Long CreateUserId = article.getCreateUserId();
        User user = userService.getById(CreateUserId);
        if (user != null) {
            String userName = user.getName();
            articleDto.setCreateUserName(userName);
        }
        return articleDto;
    }

}
