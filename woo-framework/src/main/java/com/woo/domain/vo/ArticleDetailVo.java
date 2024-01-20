package com.woo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {
    private Long id;
    /**
     *标题
     */
    private String title;
    /**
     *文章摘要
     */
    private String summary;
    /**
     *分类id
     */
    private String categoryId;
    /**
     *分类名
     */
    private String categoryName;
    /**
     *文章内容
     */
    private String content;
    /**
     *缩略名
     */
    private String thumbnail;
    /**
     *访问量
     */
    private Long viewCount;
    /**
     *
     */
    private Date createTime;
}
