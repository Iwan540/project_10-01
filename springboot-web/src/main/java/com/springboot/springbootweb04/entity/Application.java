package com.springboot.springbootweb04.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("application")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "apid", type = IdType.AUTO)
    private Integer apid;

    @TableField("auid")
    private Integer auid;

    @TableField("cid")
    private Integer cid;

    @TableField("status")
    private String status;

    @TableField("resume")
    private String resume;

    @TableField("tranciption")
    private String tranciption;

    @TableField("enrolment_details_semester")
    private String enrolmentDetailsSemester;

    @TableField("working_hours")
    private Integer workingHours;

    @TableField("overseas")
    private Boolean overseas;

    @TableField("willback")
    private Boolean willback;

    @TableField("permanent")
    private Boolean permanent;

    @TableField("visa")
    private Boolean visa;

    @TableField("contracts")
    private Boolean contracts;

    @TableField("undergraduate")
    private String undergraduate;

    @TableField("grades")
    private String grades;

    @TableField("studied")
    private Boolean studied;

    @TableField("qualification")
    private String qualification;

    @TableField("experience")
    private String experience;


    @TableField("priority")
    private Integer priority;

    @TableField("qualified")
    private Integer qualified;

    @TableField("rate")
    private String rate;

    @TableField("description")
    private String description;
}
