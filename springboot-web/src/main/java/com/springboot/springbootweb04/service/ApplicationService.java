package com.springboot.springbootweb04.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.springbootweb04.entity.Application;
import com.springboot.springbootweb04.entity.Dto.ApplicationDto;
import com.springboot.springbootweb04.mapper.ApplicationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ApplicationService extends ServiceImpl<ApplicationMapper, Application> {
    @Resource
    private ApplicationMapper applicationMapper;

    public IPage<ApplicationDto> selectable(Integer auid, Integer priority, String name, Page<ApplicationDto> page) {
        if (priority == 5 || priority == 6) {
            if (name != null && !"".equals(name.trim())) {
                return applicationMapper.selectApplyByStudentAndCourseName(page, auid, name);
            }
            return applicationMapper.selectApply(page, auid);
        } else if (!"".equals(name) && name != null) {
            return applicationMapper.selectApply1(page, name);
        } else {
            return applicationMapper.selectApply2(page);
        }
    }
}
