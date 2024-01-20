package com.woo.controller;

import com.woo.annotation.mySystemlog;
import com.woo.domain.ResponseResult;
import com.woo.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @PostMapping("/upload")
    @mySystemlog(businessName = "上传文件")
    public ResponseResult uploadImg(MultipartFile img){

        return uploadService.uploadImg(img);
    }
}
