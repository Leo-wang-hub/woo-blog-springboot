package com.woo.controller;

import com.woo.annotation.mySystemlog;
import com.woo.domain.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.woo.service.LinkService;

@RestController
@RequestMapping("/link")
@Api(tags = "友链的相关接口文档", description = "友链相关接口描述")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/getAllLink")
    @mySystemlog(businessName = "查询所有友链")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}
