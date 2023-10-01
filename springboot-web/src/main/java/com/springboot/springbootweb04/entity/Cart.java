package com.springboot.springbootweb04.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

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
@Getter
@Setter
@Accessors(chain = true)
@Data
@TableName("cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableField("auid")
    private Integer auid;


    @TableField("cid")
    private Integer cid;


    @TableField("favors")
    private Integer favors;

    @TableField("applied")
    private Boolean applied;

    @TableId(value = "cartid", type = IdType.AUTO)
    private Integer cartid;


}
