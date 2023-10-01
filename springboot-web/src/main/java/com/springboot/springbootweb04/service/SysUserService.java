package com.springboot.springbootweb04.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.springbootweb04.entity.SysUser;
import com.springboot.springbootweb04.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.annotation.Resource;

@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {
    @Resource
    private SysUserMapper sysUserMapper;


    public SysUser login(String email, String passwd) {
        return sysUserMapper.login(email, passwd);
    }

    public List<SysUser> selectAllByEmail(String email) {
        return sysUserMapper.selectAllByEmail(email);
    }

    public List<SysUser> selectAllBySname(String sname) {
        return sysUserMapper.selectAllBySname(sname);
    }



    public int register(SysUser newUser) {
        newUser.setPriority(5); // 设置默认优先级
        sysUserMapper.insert(newUser); // 将用户信息保存到数据库
        return 1; // 返回1表示注册成功
    }

    public SysUser selectByUpi(String upi) {
        return sysUserMapper.selectByUpi(upi);
    }







}
