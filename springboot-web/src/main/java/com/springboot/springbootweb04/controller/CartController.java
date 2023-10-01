package com.springboot.springbootweb04.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.springbootweb04.entity.Cart;
import com.springboot.springbootweb04.entity.Course;
import com.springboot.springbootweb04.entity.Dto.CourseDto;
import com.springboot.springbootweb04.service.CartService;
import com.springboot.springbootweb04.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public Result<?> getCart(@RequestParam Integer auid, @RequestParam Integer cur, @RequestParam Integer size) {
        List<CourseDto> res = cartService.selectPage(auid, cur, size);
        int total = cartService.getTotalCount(auid);
        Map<String, Object> result = new HashMap<>();
        result.put("data", res);
        result.put("total", total);
        return Result.success(result);
    }

    @PutMapping("/addcart")
    public Result<?> addcart(@RequestBody Cart cart) {
        if(cartService.saveOrUpdate(cart)) {
            return Result.success();
        } else {
            return Result.error("400", "erorr saving!");
        }
    }

    @GetMapping("/search")
    public Result<?> searchCoursesInCart(@RequestParam String cname, @RequestParam Integer auid) {
        List<CourseDto> searchResults = cartService.selectByCourseNameInCart(cname, auid);
        int total = cartService.getTotalCountByCourseName(cname, auid);
        Map<String, Object> result = new HashMap<>();
        result.put("data", searchResults);
        result.put("total", total);
        return Result.success(result);
    }
}
