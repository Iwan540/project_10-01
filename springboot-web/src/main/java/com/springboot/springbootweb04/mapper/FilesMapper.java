package com.springboot.springbootweb04.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.springbootweb04.entity.files;

import java.util.List;
import java.util.Map;

public interface FilesMapper extends BaseMapper<files> {
    List<files> getRecent();

    List<files> pageInfo(String project, String filename, Integer pageNum, Integer pageSize);
}
