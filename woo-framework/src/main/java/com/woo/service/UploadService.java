package com.woo.service;

import com.woo.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    public ResponseResult uploadImg(MultipartFile img);
}
