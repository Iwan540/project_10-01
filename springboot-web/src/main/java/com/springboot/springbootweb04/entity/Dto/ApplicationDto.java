package com.springboot.springbootweb04.entity.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApplicationDto implements Serializable {
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

    @TableField("cname")
    private String cname;

    @TableField("term")
    private String term;

    @TableField("upi")
    private String upi;

    @TableField("email")
    private String email;

    @TableField("sname")
    private String sname;

    @TableField("min_grade")
    private Integer minGrade;

    @TableField("requirement")
    private String requirement;
}
