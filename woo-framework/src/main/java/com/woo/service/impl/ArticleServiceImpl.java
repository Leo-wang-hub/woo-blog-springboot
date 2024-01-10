package com.woo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woo.domain.entity.Article;
import com.woo.mapper.ArticleMapper;
import com.woo.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
