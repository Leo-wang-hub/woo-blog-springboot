package com.woo.controller;

import com.woo.annotation.mySystemlog;
import com.woo.domain.ResponseResult;
import com.woo.service.CategoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(tags = "文章分类的相关接口文档", description = "文章分类接口文档描述")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @GetMapping("/getCategoryList")
    @mySystemlog(businessName = "查询所有的分类列表")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
