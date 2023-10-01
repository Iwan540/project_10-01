package com.springboot.springbootweb04.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_file")
public class files {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String filename;

    private String type;

    private Long size;

    @JsonFormat(timezone = "UTC+13", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String url;

    private Boolean isDelete;

    private Integer userId;

    @TableField(exist = false)
    private String nickname;

    private String md5;

}
