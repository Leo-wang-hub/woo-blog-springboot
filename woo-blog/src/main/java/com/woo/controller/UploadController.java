package com.woo.controller;

import com.woo.annotation.mySystemlog;
import com.woo.domain.ResponseResult;
import com.woo.service.UploadService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "文章上传的相关接口文档", description = "文章上传接口文档描述")
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @PostMapping("/upload")
    @mySystemlog(businessName = "上传文件")
    public ResponseResult uploadImg(MultipartFile img){

        return uploadService.uploadImg(img);
    }
}
