package com.springboot.springbootweb04.controller;

import com.springboot.springbootweb04.entity.ApDetails;
import com.springboot.springbootweb04.service.ApDetailsService;
import com.springboot.springbootweb04.utils.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/apdetail")
public class ApDetailsController {
    @Autowired
    private ApDetailsService apDetailsService;

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Integer id) {
        ApDetails res = apDetailsService.getById(id);
        return Result.success(res);
    }

    @DeleteMapping("/{id}")
    public Result<?> delById(@PathVariable Integer id) {
        boolean res = apDetailsService.removeById(id);
        if(res == true) {
            return Result.success(res);
        } else {
            return Result.error("400", "error delete!");
        }
    }

    @PutMapping("/addapdetails")
    public Result<?> addDetails(@RequestBody ApDetails apDetails) {
        boolean res = apDetailsService.saveOrUpdate(apDetails);
        if(res == true) {
            return Result.success(res);
        } else {
            return Result.error("400", "error saving!");
        }
    }
}
