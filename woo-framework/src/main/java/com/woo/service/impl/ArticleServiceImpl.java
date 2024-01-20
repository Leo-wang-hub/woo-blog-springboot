package com.woo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.woo.constants.SystemCantants;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Article;
import com.woo.domain.entity.Category;
import com.woo.domain.vo.ArticleDetailVo;
import com.woo.domain.vo.ArticleListVo;
import com.woo.domain.vo.HotArtcileList;
import com.woo.domain.vo.PageVo;
import com.woo.mapper.ArticleMapper;
import com.woo.service.ArticleService;
import com.woo.service.CategoryService;
import com.woo.util.BeanCopyUtils;
import com.woo.util.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author woo
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article>  queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemCantants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查询10条
        Page<Article> page = new Page(SystemCantants.ARTICLE_STATUS_CURRENT, SystemCantants.ARTICLE_STATUS_SIZE);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        return ResponseResult.okResult(BeanCopyUtils.copyBeanList(articles, HotArtcileList.class));
    }
    @Lazy
    @Autowired
    CategoryService categoryService;
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId 就要根据类别进行查询
        queryWrapper.eq(Objects.isNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //查询已经发布的文章
        queryWrapper.eq(Article::getStatus, SystemCantants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序排列
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        //查询categoryName
        articles.stream().map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName())).collect(Collectors.toList());
        //封装查询的结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        return ResponseResult.okResult(new PageVo(articleListVos, page.getTotal()));
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从Redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成Vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Category category = categoryService.getById(id);
        if (category != null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装相应
        return ResponseResult.okResult(articleDetailVo);
    }
    @Autowired
    RedisCache redisCache;
    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应的id的浏览量
        redisCache.incrementCacheMapValue("article:viewCount", id.toString(), 1);
        return ResponseResult.okResult();
    }
}
