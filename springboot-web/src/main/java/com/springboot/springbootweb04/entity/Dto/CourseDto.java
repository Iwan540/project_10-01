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
 * @since 2023-09-18
 */
@Getter
@Setter
@Accessors(chain = true)
public class CourseDto implements Serializable {

    private static final long serialVersionUID = 1L;



    private Integer cid;


    @TableField("cname")
    private String cname;


    @TableField("term")
    private String term;

    @TableField("capacity")
    private Integer capacity;

    @TableField("selected")
    private Integer selected;


    @TableField("course_coordinator")
    private String courseCoordinator;


    @TableField("course_director")
    private String courseDirector;


    @TableField("details")
    private String details;
    @TableField("hour")
    private String hour;

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

    @TableField("min_grade")
    private Integer minGrade;

    @TableField("auid")
    private Integer auid;


    @TableField("favors")
    private Integer favors;

    @TableField("applied")
    private Integer applied;

    @TableId(value = "cartid", type = IdType.AUTO)
    private Integer cartid;


    @TableField("requirement")
    private String requirement;

}
