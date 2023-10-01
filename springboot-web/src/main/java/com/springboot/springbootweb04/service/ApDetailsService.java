package com.springboot.springbootweb04.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.springbootweb04.entity.ApDetails;
import com.springboot.springbootweb04.mapper.ApDetailsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ApDetailsService extends ServiceImpl<ApDetailsMapper, ApDetails> {
    @Resource
    private ApDetailsMapper apDetailsMapper;
}
