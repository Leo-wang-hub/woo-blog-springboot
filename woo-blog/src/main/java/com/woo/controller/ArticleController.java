package com.woo.controller;

import com.woo.annotation.mySystemlog;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Article;
import com.woo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    @mySystemlog(businessName = "查询热点文章")
    public ResponseResult hotArticleList(){
        ResponseResult result = articleService.hotArticleList();
        return result;
    }
    @GetMapping("/articleList")
    @mySystemlog(businessName = "查询所有的文章列表")
    public ResponseResult articleList(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Long categoryId){
        return articleService.articleList(pageNum, pageSize, categoryId);
    }
    @GetMapping("/{id}")
    @mySystemlog(businessName = "根据id查询文章")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);

    }
    @PutMapping("/updateViewCount/{id}")
    @mySystemlog(businessName = "更新文章浏览量")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }
}
