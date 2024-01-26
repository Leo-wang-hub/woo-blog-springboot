package com.woo.controller;

import com.woo.annotation.mySystemlog;
import com.woo.constants.SystemCantants;
import com.woo.domain.ResponseResult;
import com.woo.domain.dto.addCommentDto;
import com.woo.domain.entity.Comment;
import com.woo.service.CommentService;
import com.woo.util.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论的相关接口文档", description = "评论接口相关描述")
public class CommentController {
    @Autowired
    CommentService commentService;
    @GetMapping("/commentList")
    @mySystemlog(businessName = "查询文章评论")
    public ResponseResult commentList(@RequestParam Long articleId, @RequestParam Integer pageNum, @RequestParam Integer pageSize){

        return commentService.commentList(SystemCantants.ARTICLE_COMMENT,articleId, pageNum, pageSize);
    }
    @GetMapping("linkCommentList")
    @ApiOperation(value = "友链评论里列表", notes = "获取友链评论区的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小")
    })
    @mySystemlog(businessName = "查询友链评论")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        return commentService.commentList(SystemCantants.LINK_COMMENT,null, pageNum, pageSize);
    }
    @PostMapping
    @mySystemlog(businessName = "添加评论")
    @ApiOperation(value = "添加评论", notes = "添加文章和友链的评论")
    public ResponseResult addComment(@RequestBody addCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.add(comment);
    }
}
