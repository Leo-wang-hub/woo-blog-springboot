package com.woo.cronjob;

import com.woo.domain.entity.Article;
import com.woo.service.ArticleService;
import com.woo.util.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@Component
public class UpdateViewCount {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;
    /**
     *每隔三分钟吧redis中的数据同步到mysql
     */
    @Scheduled(cron = "* 0/10 * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量 得到的是双列集合
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        // 将双列集合转换成单列集合
        List<Article> articles = viewCountMap.entrySet().stream().map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue())).collect(Collectors.toList());
        //将数据更新到数据库中
        articleService.updateBatchById(articles);
        log.info("redis的文章浏览量同步到数据库");
    }
}
