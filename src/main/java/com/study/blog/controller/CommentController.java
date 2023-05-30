package com.study.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.study.blog.common.R;
import com.study.blog.dto.CommentDto;
import com.study.blog.entity.Article;
import com.study.blog.entity.Comment;
import com.study.blog.entity.User;
import com.study.blog.service.ArticleService;
import com.study.blog.service.CommentService;
import com.study.blog.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 相关文章的控制器
 */
@RestController
@RequestMapping("comment")
@Slf4j
public class CommentController {
    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;
    @Resource
    private ArticleService articleService;

    /**
     * 查看所有评论
     *
     * @param aid
     * @return
     */
    @GetMapping()
    public R<List<CommentDto>> showComment(Integer aid) {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Comment::getArticleId, aid);
        List<Comment> commentList = commentService.list(lambdaQueryWrapper);
        List<CommentDto> list = commentList.stream()
                .map(comment -> {
                    //1.创建commentDto对象
                    CommentDto commentDto = new CommentDto();
                    //2.将comment存入commentDto
                    BeanUtils.copyProperties(comment, commentDto);
                    //3.从user中获取用户名
                    Long userId = comment.getCreateUserId();
                    User user = userService.getById(userId);
                    if (user != null) {
                        String userName = user.getName();
                        commentDto.setCreateUserName(userName);
                    }
                    return commentDto;
                })
                .collect(Collectors.toList());
        return R.success(list);
    }

    /**
     * 采纳评论
     * @param cid
     * @return
     */
    @PutMapping("/adopt/{cid}")
    @Transactional
    public R<String> adopt(@PathVariable Integer cid) {
        //1.根据评论id,获取评论对象,并设置采纳状态为已采纳
        Comment comment = commentService.getById(cid);
        if (comment == null) {
            R.error("评论不存在...");
        }
        comment.setAdopted(1);
        commentService.updateById(comment);
        //2.取消文章悬赏
        Long articleId = comment.getArticleId();
        Article article = articleService.getById(articleId);
        if (article == null) {
            R.error("文章不存在...");
        }
        Integer reward = article.getReward();
        article.setReward(0);
        articleService.updateById(article);
        //3.为评论者添加积分
        Long userId = comment.getCreateUserId();
        User user = userService.getById(userId);
        if (user == null) {
            R.error("用户不存在...");
        }
        user.setPoints(user.getPoints() + reward);
        userService.updateById(user);
        //4.为评论者添加积分.
        return R.success("采纳成功");
    }

    /**
     * 发表评论
     * 单纯是评论,并没有回复功能,日后考虑改进
     * @param comment
     * @return
     */
    @PostMapping
    public R<String> post(@RequestBody Comment comment){
        boolean save = commentService.save(comment);
        return save ? R.success("发表评论成功") : R.error("发表评论失败");
    }
//    /**这里出现了数据库设计的问题,一个用户只能点赞一次,如何判断用户已经点赞?需要一个中间表还是其他方法?
//     * 根据评论id点赞
//     * @param cid
//     * @return
//     */
//    @GetMapping("/support")
//    public R<String> support(Integer cid){
//        Comment comment = commentService.getById(cid);
//    }
}
