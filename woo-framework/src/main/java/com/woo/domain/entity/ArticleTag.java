package com.woo.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 文章标签关联表(ArticleTag)实体类
 *
 * @author makejava
 * @since 2024-01-23 15:46:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_article_tag")
public class ArticleTag {

    /**
     * 文章id
     */
    @TableId
    private Long articleId;
    /**
     * 标签id
     */
    private Long tagId;


}

