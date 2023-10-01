package com.springboot.springbootweb04.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2023-09-18
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "auid", type = IdType.AUTO)
    private Integer auid;


    @TableField("upi")
    private String upi;

    @TableField("sname")
    private String sname;


    @TableField("email")
    private String email;


    @TableField("priority")
    private Integer priority;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwd;

    @TableField(exist = false)
    private String token;
}
