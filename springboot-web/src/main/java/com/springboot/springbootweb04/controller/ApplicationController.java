package com.springboot.springbootweb04.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.springbootweb04.entity.Application;
import com.springboot.springbootweb04.entity.Dto.ApplicationDto;
import com.springboot.springbootweb04.service.ApplicationService;
import com.springboot.springbootweb04.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public Result<?> getAllApplication() {
        List<Application> res = applicationService.list();
        return Result.success(res);
    }

    @GetMapping("/select")
    public Result<?> getPage(@RequestParam Integer auid, @RequestParam Integer priority,
                             @RequestParam String cname, @RequestParam(name = "cur", defaultValue = "1") Integer current,
                             @RequestParam(defaultValue = "10") Integer size) {
        Page<ApplicationDto> page = new Page<>(current, size);
        IPage<ApplicationDto> res = applicationService.selectable(auid, priority, cname, page);
        return Result.success(res);
    }

    @PutMapping("/addapply")
    public Result<?> addapply(@RequestBody Application application) {
        if(applicationService.saveOrUpdate(application)) {
            return Result.success();
        } else {
            return Result.error("400", "error saving!");
        }
    }

    @PostMapping("/update")
    public Result<?> setStatus(@RequestBody Application application) {
        boolean res = applicationService.updateById(application);
        return Result.success(res);
    }
}
