package com.woo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woo.constants.SystemCantants;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Article;
import com.woo.domain.entity.Category;
import com.woo.domain.vo.CategoryVo;
import com.woo.mapper.CategoryMapper;
import com.woo.service.ArticleService;
import com.woo.service.CategoryService;
import com.woo.util.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2024-01-16 09:15:45
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
  @Autowired
  private ArticleService articleService;
  @Override
  public ResponseResult getCategoryList() {
   //1. 查询文章表， 状态已发布的文章
   LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
   articleWrapper.eq(Article::getStatus, SystemCantants.ARTICLE_STATUS_NORMAL);
   List<Article> articleList = articleService.list(articleWrapper);
   //2. 获取文章的分类id，并且去重

   Set<Long> collectIds = articleList.stream().map(article -> article.getCategoryId()).collect(Collectors.toSet());

   //3. 查询分类表
   List<Category> categories = listByIds(collectIds);
   categories = categories.stream().filter(category -> SystemCantants.STATUS_NORMAL.equals(category.getStatus())).collect(Collectors.toList());
   //4. 封装vo
   List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
   return ResponseResult.okResult(categoryVos);

  }
 }
