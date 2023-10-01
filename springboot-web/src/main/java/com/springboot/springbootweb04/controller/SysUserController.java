package com.springboot.springbootweb04.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.springbootweb04.entity.SysUser;
import com.springboot.springbootweb04.service.SysUserService;
import com.springboot.springbootweb04.utils.Result;
import com.springboot.springbootweb04.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sysuser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping
    public Result<?> getAllUser() {
        List<SysUser> res = sysUserService.list();
        return Result.success(res);
    }

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam Integer cur, @RequestParam Integer size) {
        IPage<SysUser> ipage = new Page<>(cur, size);
        IPage<SysUser> res = sysUserService.page(ipage);
        return Result.success(res);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody SysUser loginUser) {
        SysUser user = sysUserService.login(loginUser.getEmail(), loginUser.getPasswd());
        if(user == null) {
            return Result.error("400", "error email or password!");
        } else {
            String token = TokenUtils.genToken(String.valueOf(user.getAuid()), user.getPasswd());
            user.setToken(token);
            return Result.success(user);
        }
    }

    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody SysUser sysUser) {
        Boolean res = sysUserService.saveOrUpdate(sysUser);
        return Result.success(res);
    }
    @GetMapping("/search")
    public Result<?> selectAllByEmail(@RequestParam String email) {
        List<SysUser> users = sysUserService.selectAllByEmail(email);
        if (users == null || users.isEmpty()) {
            return Result.error("404", "Users not found by email!");
        }
        return Result.success(users);
    }

    @GetMapping("/search2")
    public Result<?> selectAllBySname(@RequestParam String sname) {
        List<SysUser> users = sysUserService.selectAllBySname(sname);
        if (users == null || users.isEmpty()) {
            return Result.error("404", "Users not found by name!");
        }
        return Result.success(users);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody SysUser newUser) {
        // 验证电子邮件是否已被注册
        List<SysUser> usersByEmail = sysUserService.selectAllByEmail(newUser.getEmail());
        if (!usersByEmail.isEmpty()) {
            return Result.error("400", "Email already registered!");
        }

        // 验证upi是否已经存在
        SysUser userByUpi = sysUserService.selectByUpi(newUser.getUpi());
        if (userByUpi != null) {
            return Result.error("400", "UPI already exists!");
        }

        // 如果都没问题，则尝试将新用户保存到数据库中
        int result = sysUserService.register(newUser);
        if(result == 1) {
            return Result.success("Registered successfully");
        } else {
            return Result.error("500", "Registration failed");
        }
    }






}
