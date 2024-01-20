package com.woo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woo.constants.SystemCantants;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Comment;
import com.woo.domain.vo.CommentVo;
import com.woo.domain.vo.PageVo;
import com.woo.enums.AppHttpCodeEnum;
import com.woo.exception.SystemException;
import com.woo.mapper.CommentMapper;
import com.woo.service.CommentService;
import com.woo.service.UserService;
import com.woo.util.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2024-01-18 14:55:35
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    UserService userService;
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
       //查询文章对应的根评论
       LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
       //对articleId进行进行判断
       queryWrapper.eq(SystemCantants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId,articleId);
       queryWrapper.eq(Comment::getRootId, SystemCantants.COMMENT_ROOT);
       queryWrapper.eq(Comment::getType,commentType);
       //分页查询
       Page<Comment> page = new Page<Comment>(pageNum, pageSize);
       page(page, queryWrapper);
       List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
       //查询所有评论对应的子评论集合
        commentVoList  = commentVoList.stream().map(commentVo -> {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
            return commentVo;
        }).collect(Collectors.toList());
        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
   }

    @Override
    public ResponseResult add(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        List<CommentVo> commentVoList = commentVos.stream().map(commentVo -> {
            //查询用户名称
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if (commentVo.getToCommentId() != -1) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
            return commentVo;
        }).collect(Collectors.toList());
        return commentVoList;
    }
}
