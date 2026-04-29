package com.filesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filesystem.entity.SysFile;
import com.filesystem.entity.User;
import com.filesystem.enums.LogAction;
import com.filesystem.enums.UserRole;
import com.filesystem.exception.BusinessException;
import com.filesystem.mapper.FileMapper;
import com.filesystem.service.FileService;
import com.filesystem.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 文件服务实现类
 *
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private LogService logService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFile uploadFile(MultipartFile file, User user) throws IOException {
        // 检查文件是否为空
        if (file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }

        // 获取文件信息
        String fileName = file.getOriginalFilename();
        long fileSize = file.getSize();
        String mimeType = file.getContentType();

        // 读取文件内容
        byte[] fileContent = file.getBytes();

        // 生成ID
        Long maxId = fileMapper.selectCount(null);
        Long fileId = maxId + 1;

        // 创建文件记录
        SysFile sysFile = new SysFile();
        sysFile.setId(fileId);
        sysFile.setFileName(fileName);
        sysFile.setFileSize(fileSize);
        sysFile.setMimeType(mimeType);
        sysFile.setFileContent(fileContent);
        sysFile.setUploadedBy(user.getId());
        sysFile.setCreatedAt(LocalDateTime.now());

        fileMapper.insert(sysFile);

        // 记录上传日志
        logService.recordLog(user.getId(), user.getUsername(), LogAction.UPLOAD,
                fileId, fileName, null);

        return sysFile;
    }

    @Override
    public Page<SysFile> getFileList(int page, int pageSize, User user) {
        Page<SysFile> pageParam = new Page<>(page, pageSize);

        // 如果是管理员，查看所有文件；普通用户只看自己的文件
        if (UserRole.ADMIN.getCode().equals(user.getRole())) {
            return fileMapper.selectPage(pageParam, null);
        } else {
            LambdaQueryWrapper<SysFile> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysFile::getUploadedBy, user.getId());
            wrapper.orderByDesc(SysFile::getCreatedAt);
            return fileMapper.selectPage(pageParam, wrapper);
        }
    }

    @Override
    public SysFile getFileDetail(Long id, User user) {
        SysFile file = fileMapper.selectById(id);
        if (file == null) {
            throw new BusinessException("文件不存在");
        }

        // 检查权限：管理员可以查看所有文件，普通用户只能查看自己的文件
        if (!UserRole.ADMIN.getCode().equals(user.getRole()) &&
                !file.getUploadedBy().equals(user.getId())) {
            throw new BusinessException("无权限访问此文件");
        }

        return file;
    }

    @Override
    public SysFile downloadFile(Long id, User user, String ipAddress) {
        SysFile file = fileMapper.selectById(id);
        if (file == null) {
            throw new BusinessException("文件不存在");
        }

        // 检查权限：管理员可以下载所有文件，普通用户只能下载自己的文件
        if (!UserRole.ADMIN.getCode().equals(user.getRole()) &&
                !file.getUploadedBy().equals(user.getId())) {
            throw new BusinessException("无权限下载此文件");
        }

        // 记录下载日志
        logService.recordLog(user.getId(), user.getUsername(), LogAction.DOWNLOAD,
                id, file.getFileName(), ipAddress);

        return file;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(Long id, User user) {
        SysFile file = fileMapper.selectById(id);
        if (file == null) {
            throw new BusinessException("文件不存在");
        }

        // 只有管理员可以删除文件
        if (!UserRole.ADMIN.getCode().equals(user.getRole())) {
            throw new BusinessException("只有管理员可以删除文件");
        }

        fileMapper.deleteById(id);

        // 记录删除日志
        logService.recordLog(user.getId(), user.getUsername(), LogAction.DELETE,
                id, file.getFileName(), null);
    }
}
