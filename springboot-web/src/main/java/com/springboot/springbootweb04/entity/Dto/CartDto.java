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
public class CartDto implements Serializable {

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
