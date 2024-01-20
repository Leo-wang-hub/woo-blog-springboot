package com.woo.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.woo.domain.ResponseResult;
import com.woo.enums.AppHttpCodeEnum;
import com.woo.exception.SystemException;
import com.woo.service.UploadService;
import com.woo.util.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Data
@ConfigurationProperties(prefix = "oss")
@Component
public class UploadServiceImpl implements UploadService {
    //TODO OSS相关设置   评论区的恢复还有问题 需要再看看
    private String accessKey;
    private String secretKey;
    private String bucket;
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if(!originalFilename.endsWith(".png")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath);
        return ResponseResult.okResult(url);
    }

    private String uploadOss(MultipartFile img, String filePath) {
        //构造一个带指定Regin对象的配置类
        Configuration configuration = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(configuration);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        try {
            InputStream inputStream = img.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response reponse = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传的结果
                DefaultPutRet putRet = new Gson().fromJson(reponse.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException e) {
                e.printStackTrace();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            //TODO OSS空间域名
            return "http://r7yxkqloa.bkt.clouddn.com/"+key;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "www";
    }
}

