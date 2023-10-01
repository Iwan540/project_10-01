package com.springboot.springbootweb04.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.springbootweb04.entity.Course;
import com.springboot.springbootweb04.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseService extends ServiceImpl<CourseMapper, Course> {
    @Resource
    private CourseMapper courseMapper;


    public IPage<Course> selectCourseWithCart(IPage<Course> ipage, Integer cur, Integer size, String cname, Integer auid) {
        List<Course> records;
        Integer offset = (cur-1)*size;
        if("".equals(cname)) {
            records = courseMapper.selectCourseWithCart1(offset, size, auid);
        } else {
            records = courseMapper.selectCourseWithCart(offset, size, cname, auid);
        }

        int total = courseMapper.selectCourseWithCartCount(cname, auid);  // 获取真实的total

        return new Page<Course>(ipage.getCurrent(), ipage.getSize(), total).setRecords(records);  // 使用真实的total
    }


    public IPage<Course> selectCourseWithCart2(IPage<Course> ipage, Integer cur, Integer size, String cname, Integer auid, Integer priority) {
        List<Course> records;
        Integer offset = (cur-1)*size;

        int total = courseMapper.countCourseWithCart2(cname);

        if (priority == 1 || priority == 3) {
            records = courseMapper.selectCourseWithCart2(offset, size, cname); // 使用selectCourseWithCart2方法进行查询
        } else {
            throw new IllegalArgumentException("Invalid priority");
        }

        return new Page<Course>(ipage.getCurrent(), ipage.getSize(), total).setRecords(records);
    }

    public boolean hasCoursesForTerm(String term) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("term", term); // 这里假设你的Course实体类中有一个名为"term"的字段
        return courseMapper.selectCount(queryWrapper) > 0;
    }








}
