package com.woo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Article;

public interface ArticleService  extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
}
