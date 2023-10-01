package com.springboot.springbootweb04.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.springbootweb04.entity.files;
import com.springboot.springbootweb04.service.FilesService;
import com.springboot.springbootweb04.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.lang.Console.print;

@RestController
@RequestMapping("/file")
public class FilesController {

    @Value("D:/files/upload")
    private String dirPath;

    @Autowired
    private FilesService filesService;

    @PostMapping("/upload")
    public Result<?> upload(@RequestParam MultipartFile file, @RequestParam Integer userId) throws IOException {
        //1. 获取原来文件的信息
        String previousName = file.getOriginalFilename();
        String type = FileUtil.extName(previousName);
        Long size = file.getSize();

        //2. 如果上传路径不存在则创建响应目录
        File dir = new File(dirPath);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        //3. 定义文件标识码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + StrUtil.DOT + type;
        File uploadFile = new File(dirPath + fileUUID);
        String url;

        //4. 获取文件的md5，防止重复存取
        file.transferTo(uploadFile);
        String md5 = SecureUtil.md5(uploadFile);
        files dbfiles = getFileByMd5(md5);

        if(dbfiles != null) {
            url = dbfiles.getUrl();
            uploadFile.delete();
        } else {
            url = "http://localhost:8088/file/" + fileUUID;
        }


        //5. 将上传的文件信息保存到数据库
        files saveFile = new files();
        saveFile.setFilename(previousName);
        saveFile.setType(type);
        saveFile.setSize(size);
        saveFile.setUrl(url);
        saveFile.setIsDelete(false);
        saveFile.setMd5(md5);
        saveFile.setUserId(userId);
        filesService.save(saveFile);
        return Result.success(url);
    }

    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        File downloadFile = new File(dirPath + fileUUID);

        //设置输出流的格式
        ServletOutputStream out = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID,"UTF-8"));
        response.setContentType("application/octet-stream");

        //读取文件的字节流
        out.write(FileUtil.readBytes(downloadFile));
        out.flush();
        out.close();
    }

    @GetMapping
    public Result<?> getFiles(@RequestParam String project, @RequestParam String filename) {
        Page<files> page = new Page<>();
        QueryWrapper<files> queryWrapper = new QueryWrapper<>();

        if(!"".equals(project)) {
            queryWrapper.eq("project", project);
        }
        if(!"".equals(filename)) {
            queryWrapper.like("filename", filename);
        }
        queryWrapper.eq("is_delete", 0);
        Page<files> page1 = filesService.page(page,queryWrapper);
        return Result.success(page1);
    }

    @GetMapping("/recent")
    public Result<?> getRecent() {
        List<files> res = filesService.getRecent();
        return Result.success(res);
    }

    @GetMapping("/page")
    public Result<?> pageInfo(@RequestParam String project, @RequestParam String filename, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        QueryWrapper<files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", 0);
        if(!"".equals(project))
        queryWrapper.like("project", project);
        if(!"".equals(filename))
        queryWrapper.like("filename", filename);
        long count = filesService.count(queryWrapper);

        project = "%" + project + "%";
        filename = "%" + filename + "%";
        pageNum = (pageNum - 1) * pageSize;
        List<files> list = filesService.pageInfo(project, filename, pageNum, pageSize);
        Map<String, Object> res = new HashMap<>();
        res.put("page", list);
        res.put("count", count);
        return Result.success(res);
    }

    @PutMapping
    public Result<?> edit(@RequestBody files file) {
        boolean res = filesService.updateById(file);
        return Result.success(res);
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteFile(@PathVariable Integer id) {
        files file = filesService.getById(id);
        file.setIsDelete(true);
        boolean res = filesService.updateById(file);
        return Result.success(res);
    }

    private files getFileByMd5(String md5) {
        QueryWrapper<files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<files> list = filesService.list(queryWrapper);
        return list.size() == 0 ? null : list.get(0);
    }
}
