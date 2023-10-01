package com.springboot.springbootweb04.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.springbootweb04.entity.Application;
import com.springboot.springbootweb04.entity.Dto.ApplicationDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ApplicationMapper extends BaseMapper<Application> {

    @Select("select application.*, course.cname, course.term, course.min_grade, course.requirement, sys_user.sname, sys_user.upi, sys_user.email  from  application inner join course on application.cid = course.cid inner join sys_user on application.auid = sys_user.auid where application.auid = #{auid}")
    IPage<ApplicationDto> selectApply(Page<ApplicationDto> page, @Param("auid") Integer auid);

    @Select("select application.*, course.cname, course.term, course.min_grade, course.requirement, sys_user.sname, sys_user.upi, sys_user.email from application inner join course on application.cid = course.cid inner join sys_user on application.auid = sys_user.auid where course.cname LIKE CONCAT('%', #{name}, '%')")
    IPage<ApplicationDto> selectApply1(Page<ApplicationDto> page, @Param("name") String name);

    @Select("select application.*, course.cname, course.term, course.min_grade, course.requirement, sys_user.sname, sys_user.upi, sys_user.email from application inner join course on application.cid = course.cid inner join sys_user on application.auid = sys_user.auid")
    IPage<ApplicationDto> selectApply2(Page<ApplicationDto> page);

    @Select("select application.*, course.cname, course.term, course.min_grade, course.requirement, sys_user.sname, sys_user.upi, sys_user.email from application inner join course on application.cid = course.cid inner join sys_user on application.auid = sys_user.auid where application.auid = #{auid} and course.cname LIKE CONCAT('%', #{name}, '%')")
    IPage<ApplicationDto> selectApplyByStudentAndCourseName(Page<ApplicationDto> page, @Param("auid") Integer auid, @Param("name") String name);
}
