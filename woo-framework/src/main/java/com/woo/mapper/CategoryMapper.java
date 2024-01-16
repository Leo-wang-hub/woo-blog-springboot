package com.woo.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woo.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2024-01-16 09:15:40
 */
public interface CategoryMapper extends BaseMapper<Category> {

}
