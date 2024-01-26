package com.woo.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woo.constants.SystemCantants;
import com.woo.domain.ResponseResult;
import com.woo.domain.entity.Link;
import com.woo.domain.vo.LinkVo;
import com.woo.domain.vo.PageVo;
import com.woo.mapper.LinkMapper;
import com.woo.util.BeanCopyUtils;
import org.springframework.stereotype.Service;
import com.woo.service.LinkService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

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

    @Override
    public PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(link.getName()), Link::getName, link.getName());
        queryWrapper.eq(Objects.nonNull(link.getStatus()), Link::getStatus, link.getStatus());
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        return new PageVo(page.getRecords(), page.getTotal());
    }
}
