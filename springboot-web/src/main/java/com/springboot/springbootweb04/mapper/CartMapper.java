package com.springboot.springbootweb04.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.springbootweb04.entity.Cart;
import com.springboot.springbootweb04.entity.Dto.CourseDto;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper extends BaseMapper<Cart> {

    @Select("select course.*, cart.* from course, cart where course.cid = cart.cid and cart.auid = #{auid}")
    IPage<CourseDto> selectP(Page<CourseDto> page, @Param("auid") Integer auid);

    @Select("SELECT COUNT(*) FROM cart WHERE auid = #{auid}")
    int getTotalCount(Integer auid);

    @Select("select course.*, cart.* from course JOIN cart ON course.cid = cart.cid where course.cname LIKE CONCAT('%', #{cname}, '%') and cart.auid = #{auid}")
    List<CourseDto> selectByCourseNameInCart(@Param("cname") String cname, @Param("auid") Integer auid);

    @Select("SELECT COUNT(*) FROM course JOIN cart ON course.cid = cart.cid WHERE course.cname LIKE CONCAT('%', #{cname}, '%') and cart.auid = #{auid}")
    int getTotalCountByCourseName(@Param("cname") String cname, @Param("auid") Integer auid);
}
