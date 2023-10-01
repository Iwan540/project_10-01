package com.springboot.springbootweb04.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.springbootweb04.entity.Notice;
import com.springboot.springbootweb04.mapper.NoticeMapper;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> {

    @Resource
    private NoticeMapper noticeMapper;

    // 获取最新通知
    public IPage<Notice> selectNewest(Page<Notice> page) {
        return noticeMapper.selectNewest(page);
    }

    // 根据日期过滤通知
    public IPage<Notice> selectByDate(Page<Notice> page, String date) {
        return noticeMapper.selectByDate(page, date);
    }

    // 删除通知
    public boolean deleteNoticeById(Integer nid) {
        return noticeMapper.deleteNoticeById(nid) > 0;
    }


}
