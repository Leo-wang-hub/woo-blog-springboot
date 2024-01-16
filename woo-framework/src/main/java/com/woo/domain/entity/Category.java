package com.woo.domain.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 分类表(Category)实体类
 *
 * @author makejava
 * @since 2024-01-16 09:15:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_category")
public class Category {


    private Long id;
    /**
     * 分类名
     */
    private String name;
    /**
     * 父分类id，如果没有父分类为-1
     */
    private Long pid;
    /**
     * 描述
     */
    private String description;
    /**
     * 状态0:正常,1禁用
     */
    private String status;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    private Integer delFlag;


}

