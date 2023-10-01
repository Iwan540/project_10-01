package com.springboot.springbootweb04.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.springbootweb04.entity.Cart;
import com.springboot.springbootweb04.entity.Course;
import com.springboot.springbootweb04.entity.SysUser;
import com.springboot.springbootweb04.service.CartService;
import com.springboot.springbootweb04.service.CourseService;
import com.springboot.springbootweb04.service.SysUserService;
import com.springboot.springbootweb04.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public Result<?> getAllCourse() {
        List<Course> res = courseService.list();
        return Result.success(res);
    }

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam Integer cur, @RequestParam Integer size, @RequestParam(defaultValue = "") String cname, @RequestParam Integer auid) {
        IPage<Course> ipage = new Page<>(cur, size);
        IPage<Course> res = courseService.selectCourseWithCart(ipage, cur, size, cname, auid);
        return Result.success(res);
    }

//    @PutMapping("/edit")
//    public Result<?> editCourse(@RequestBody Course course) {
//        boolean res = courseService.updateById(course);
//        if(res == true) {
//            return Result.success(res);
//        } else {
//            return Result.error("400", "erorr updating!");
//        }
//    }
    @GetMapping("/page2")
    public Result<?> getCourseWithCart2(@RequestParam Integer cur,
                                        @RequestParam Integer size,
                                        @RequestParam(defaultValue = "") String cname,
                                        @RequestParam Integer auid,
                                        @RequestParam Integer priority) {
        IPage<Course> ipage = new Page<>(cur, size);
        IPage<Course> res = courseService.selectCourseWithCart2(ipage, cur, size, cname, auid, priority);
        return Result.success(res);
    }

    @DeleteMapping("/del/{id}")
    public Result<?> deleteCourse(@PathVariable Integer id) {
        Boolean res =  courseService.removeById(id);
        return Result.success(res);
    }

    @PutMapping("/update")
    public Result<?> updateCourse(@RequestBody Course course) {
        boolean res = courseService.saveOrUpdate(course);
        return Result.success(res);
    }

    @PostMapping("/batchadd")
    public Result<?> addBatchCourse(@RequestParam String term, @RequestParam String newTerm) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("term", term);
        List<Course> existingCourses = courseService.list(queryWrapper);

        // 检查是否存在课程
        if(existingCourses.isEmpty()) {
            return Result.error("400", "该年没有课程，无法生成新课程");
        }

        for(Course course : existingCourses) {
            course.setCid(null);  // 移除ID以创建新的记录
            course.setTerm(newTerm);  // 更新学期
        }
        courseService.saveBatch(existingCourses);  // 批量保存更新后的课程
        return Result.success();
    }

//    @PutMapping("/save")
//    public Result<?> addCourse(@RequestBody Course course) {
//        boolean res = courseService.save(course);
//        return Result.success(res);
//    }


}
