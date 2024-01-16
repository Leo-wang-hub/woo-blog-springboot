package com.woo.controller;

import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Article;
import com.woo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        ResponseResult result = articleService.hotArticleList();
        return result;
    }
    @GetMapping("/articleList")
    public ResponseResult articleList(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Long categoryId){
        return articleService.articleList(pageNum, pageSize, categoryId);
    }
}
