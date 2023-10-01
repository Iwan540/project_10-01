package com.springboot.springbootweb04.entity;

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
@TableName("ap_details")
public class ApDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("apid")
    private Integer apid;

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

    @TableField("winback")
    private Boolean winback;

    @TableField("permanent")
    private Boolean permanent;

    @TableField("visa")
    private Boolean visa;

    @TableField("contracts")
    private Boolean contracts;

    @TableField("undergraduate")
    private Boolean undergraduate;

    @TableField("grades")
    private String grades;

    @TableField("studied")
    private Boolean studied;

    @TableField("experience")
    private String experience;

    @TableField("description")
    private String description;


}
