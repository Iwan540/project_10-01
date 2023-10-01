package com.springboot.springbootweb04.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.springbootweb04.entity.files;
import com.springboot.springbootweb04.mapper.FilesMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FilesService extends ServiceImpl<FilesMapper, files> {
    @Resource
    private FilesMapper filesMapper;

    public List<files> getRecent() {
        return filesMapper.getRecent();
    }
    public List<files> pageInfo(String project, String filename, Integer pageNum, Integer pageSize) {
        return filesMapper.pageInfo(project, filename,pageNum, pageSize);
    }
}
