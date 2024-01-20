package com.woo.controller;

import com.woo.annotation.mySystemlog;
import com.woo.constants.SystemCantants;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Comment;
import com.woo.service.CommentService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @GetMapping("/commentList")
    @mySystemlog(businessName = "查询文章评论")
    public ResponseResult commentList(@RequestParam Long articleId, @RequestParam Integer pageNum, @RequestParam Integer pageSize){

        return commentService.commentList(SystemCantants.ARTICLE_COMMENT,articleId, pageNum, pageSize);
    }
    @GetMapping("linkCommentList")
    @mySystemlog(businessName = "查询友链评论")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemCantants.LINK_COMMENT,null, pageNum, pageSize);
    }
    @PostMapping
    @mySystemlog(businessName = "添加评论")
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.add(comment);
    }
}
