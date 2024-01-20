package com.woo.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Comment;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2024-01-18 14:55:28
 */
public interface CommentService extends IService<Comment> {
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult add(Comment comment);
}
