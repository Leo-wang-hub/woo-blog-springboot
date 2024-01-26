package com.woo.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserVo {
    private Long id;
    private String userName;
    private String nickName;
    private String status;
    private String email;
    private String phoneNumber;
    private String sex;
    private String avatar;
    private Long createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
}
