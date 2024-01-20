package com.woo.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Link;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2024-01-17 09:28:37
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
