package com.woo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woo.domain.ResponseResult;
import com.woo.domain.dto.AddArticleDto;
import com.woo.domain.dto.ArticleDto;
import com.woo.domain.entity.Article;

public interface ArticleService  extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult selectArticlePage(Article article, Integer pageNum, Integer pageSize);

    ResponseResult getInfo(Long id);

    void edit(ArticleDto articleDto);
}
