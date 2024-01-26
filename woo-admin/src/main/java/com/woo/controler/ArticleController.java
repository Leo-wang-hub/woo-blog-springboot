package com.woo.controler;

import com.woo.domain.ResponseResult;
import com.woo.domain.dto.AddArticleDto;
import com.woo.domain.dto.ArticleDto;
import com.woo.domain.entity.Article;
import com.woo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping
    @PreAuthorize("@ps.hasPermission('content:article:writer')")
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }
    @GetMapping("/list")
    public ResponseResult listAll(Article article, Integer pageSize, Integer pageNum) {
        ResponseResult result = articleService.selectArticlePage(article, pageNum, pageSize);
        return result;
    }
    @GetMapping("{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id) {
        return articleService.getInfo(id);
    }
    @PutMapping
    public ResponseResult edit(@RequestBody ArticleDto articleDto){
        articleService.edit(articleDto);
        return ResponseResult.okResult();
    }
    @DeleteMapping("{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        articleService.removeById(id);
        return ResponseResult.okResult();
    }
}
