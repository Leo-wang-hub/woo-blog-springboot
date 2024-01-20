package com.woo.controller;

import com.woo.annotation.mySystemlog;
import com.woo.domain.ResponseResult;
import com.woo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @GetMapping("/getCategoryList")
    @mySystemlog(businessName = "查询所有的分类列表")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
