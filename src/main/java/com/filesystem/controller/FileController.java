package com.filesystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filesystem.entity.SysFile;
import com.filesystem.entity.User;
import com.filesystem.service.FileService;
import com.filesystem.util.IpUtil;
import com.filesystem.vo.PageInfo;
import com.filesystem.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 文件控制器
 *
 */
@Slf4j
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件
     */
    @PostMapping
    public Result<SysFile> uploadFile(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        log.info("用户 {} 上传文件: {}, 大小: {}",
                user.getUsername(), file.getOriginalFilename(), file.getSize());

        SysFile sysFile = fileService.uploadFile(file, user);

        log.info("文件上传成功: id={}, name={}", sysFile.getId(), sysFile.getFileName());
        return Result.success("上传成功", sysFile);
    }

    /**
     * 获取文件列表
     */
    @GetMapping
    public Result<PageInfo<SysFile>> getFileList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        log.info("用户 {} 获取文件列表: page={}, pageSize={}", user.getUsername(), page, pageSize);

        Page<SysFile> filePage = fileService.getFileList(page, pageSize, user);

        PageInfo<SysFile> pageInfo = PageInfo.of(filePage.getTotal(), filePage.getRecords());
        return Result.success(pageInfo);
    }

    /**
     * 获取文件详情
     */
    @GetMapping("/{id}")
    public Result<SysFile> getFileDetail(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        log.info("用户 {} 获取文件详情: id={}", user.getUsername(), id);

        SysFile file = fileService.getFileDetail(id, user);

        return Result.success(file);
    }

    /**
     * 下载文件
     */
    @GetMapping("/{id}/download")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @PathVariable Long id,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        log.info("用户 {} 下载文件: id={}", user.getUsername(), id);

        SysFile file = fileService.downloadFile(id, user, IpUtil.getIpAddress(request));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.getMimeType()));
        headers.setContentDispositionFormData("attachment", file.getFileName());

        ByteArrayResource resource = new ByteArrayResource(file.getFileContent());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.getFileSize())
                .body(resource);
    }

    /**
     * 删除文件（管理员）
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteFile(@PathVariable Long id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        log.info("用户 {} 删除文件: id={}", user.getUsername(), id);

        fileService.deleteFile(id, user);

        log.info("文件删除成功: id={}", id);
        return Result.success();
    }
}
