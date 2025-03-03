package com.woo.runner;

import com.woo.domain.entity.Article;
import com.woo.mapper.ArticleMapper;
import com.woo.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("==========博客项目启动成功==========");
        // 查询数据库中的博客信息
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap= articles.stream().collect(Collectors.toMap(article -> article.getId().toString(),article ->article.getViewCount().intValue()));
        redisCache.setCacheMap("article:viewCount", viewCountMap);
    }
}
