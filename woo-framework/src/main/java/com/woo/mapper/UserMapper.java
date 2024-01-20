package com.woo.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woo.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* 用户表(User)表数据库访问层
*
* @author makejava
* @since 2024-01-17 10:09:05
*/
public interface UserMapper extends BaseMapper<User> {

}
