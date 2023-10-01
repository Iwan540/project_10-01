package com.springboot.springbootweb04.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
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
@TableName("course")

public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cid", type = IdType.AUTO)
    private Integer cid;

    @TableField("cname")
    private String cname;

    @TableField("term")
    private String term;

    @TableField("capacity")
    private Integer capacity;

    @TableField("course_coordinator")
    private String courseCoordinator;

    @TableField("course_director")
    private String courseDirector;

    @TableField("selected")
    public Integer selected;

    @TableField("details")
    private String details;

    @TableField("min_grade")
    private Integer minGrade;

    @TableField("visible")
    private Boolean visible;

    @TableField("coursecoordinatorname")
    private String coursecoordinatorname;

    @TableField("coursecoordinatoremail")
    private String coursecoordinatoremail;

    @TableField("coursedirectorname")
    private String coursedirectorname;

    @TableField("coursedirectoremail")
    private String coursedirectoremail;

    @TableField("marker")
    private Boolean marker;

    @TableField("hour")
    private String hour;

    @TableField("requirement")
    private String requirement;



}
