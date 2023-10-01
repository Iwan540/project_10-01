package com.springboot.springbootweb04.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.springbootweb04.entity.Notice;
import com.springboot.springbootweb04.service.NoticeService;
import com.springboot.springbootweb04.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    // 获取通知列表
    @GetMapping
    public Result<List<Notice>> getNotice() {
        List<Notice> res = noticeService.list();
        return Result.success(res);
    }

    // 获取最新通知
    @GetMapping("/newest")
    public Result<IPage<Notice>> getNoticeNewest(@RequestParam(defaultValue = "1") int cur, @RequestParam(defaultValue = "10") int size) {
        Page<Notice> pageObj = new Page<>(cur, size);
        IPage<Notice> res = noticeService.selectNewest(pageObj);
        return Result.success(res);
    }

    // 添加通知
    @PutMapping("/addnotice")
    public Result<Boolean> addNotice(@RequestBody Notice notice) {
        boolean res = noticeService.saveOrUpdate(notice);
        return Result.success(res);
    }

    // 根据日期过滤通知
    @GetMapping("/bydate")
    public Result<IPage<Notice>> getNoticeByDate(@RequestParam String date, @RequestParam(defaultValue = "1") int cur, @RequestParam(defaultValue = "10") int size) {
        Page<Notice> pageObj = new Page<>(cur, size);
        IPage<Notice> res = noticeService.selectByDate(pageObj, date);
        return Result.success(res);
    }

    // 删除通知
    @DeleteMapping("/{nid}")
    public Result<Boolean> deleteNotice(@PathVariable Integer nid) {
        boolean res = noticeService.deleteNoticeById(nid);
        return Result.success(res);
    }
}
