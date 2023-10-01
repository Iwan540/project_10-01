package com.springboot.springbootweb04.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.springbootweb04.entity.Course;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CourseMapper extends BaseMapper<Course> {
    //学生
    @Select("select course.* from course where course.cid not in (select cart.cid from cart where cart.auid = #{auid}) and course.cname LIKE CONCAT('%', #{cname}, '%') and course.visible = 1 limit #{size} offset #{offset}")
    List<Course> selectCourseWithCart(Integer offset, Integer size, String cname, Integer auid);

    //学生
    @Select("select course.* from course where course.cid not in (select cart.cid from cart where cart.auid = #{auid}) and course.visible = 1 limit #{size} offset #{offset}")
    List<Course> selectCourseWithCart1(Integer offset, Integer size, Integer auid);
    //
    @Select("select course.* from course where course.cname LIKE CONCAT('%', #{cname}, '%') limit #{size} offset #{offset}")
    List<Course> selectCourseWithCart2(Integer offset, Integer size, String cname);

    //学生
    @Select("select count(*) from course where course.cid not in (select cart.cid from cart where cart.auid = #{auid}) and course.cname LIKE CONCAT('%', #{cname}, '%') and course.visible = 1")
    int selectCourseWithCartCount(String cname, Integer auid);

    @Select("select count(*) from course where course.cname LIKE CONCAT('%', #{cname}, '%')")
    int countCourseWithCart2(String cname);
















}
