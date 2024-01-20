package com.woo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woo.constants.SystemCantants;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Link;
import com.woo.domain.vo.LinkVo;
import com.woo.mapper.LinkMapper;
import com.woo.util.BeanCopyUtils;
import org.springframework.stereotype.Service;
import com.woo.service.LinkService;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2024-01-17 09:28:37
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemCantants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        //转换成Vo
        List<LinkVo> linkVo = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        //封装返回
        return ResponseResult.okResult(linkVo);
    }
}
