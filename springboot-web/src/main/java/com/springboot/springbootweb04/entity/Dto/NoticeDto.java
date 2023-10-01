package com.springboot.springbootweb04.entity.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2023-09-28
 */
@Getter
@Setter
@Accessors(chain = true)
public class NoticeDto implements Serializable {
    private static final long serialVersionUID = 1L;


    @TableId(value = "nid", type = IdType.AUTO)
    private Integer nid;


    @TableField("date")
    @JsonFormat(timezone = "UTC+13", pattern = "yyyy-MM-dd HH:mm:ss")
    private String date;


    @TableField("content")
    private String content;

    @TableField("auid")
    private Integer auid;


}
