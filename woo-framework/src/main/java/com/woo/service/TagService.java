package com.woo.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Tag;
import com.woo.domain.dto.TagListDto;
import com.woo.domain.vo.TagVo;

import java.util.List;

/**
 * 标签(SgTag)表服务接口
 *
 * @author makejava
 * @since 2024-01-22 11:00:17
 */
public interface TagService extends IService<Tag> {

    ResponseResult pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    List<TagVo> listAllTag();
}
