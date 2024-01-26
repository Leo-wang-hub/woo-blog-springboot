package com.woo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woo.domain.entity.ArticleTag;
import com.woo.mapper.ArticleTagMapper;
import com.woo.service.ArticleTagService;
import org.springframework.stereotype.Component;

@Component
public class ArticleTagServiceImp extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
}
