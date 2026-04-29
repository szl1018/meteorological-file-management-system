package com.filesystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filesystem.entity.SysFile;
import com.filesystem.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件服务接口
 *
 */
public interface FileService {

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @param user 当前用户
     * @return 文件信息
     */
    SysFile uploadFile(MultipartFile file, User user) throws IOException;

    /**
     * 获取文件列表
     *
     * @param page      页码
     * @param pageSize 每页数量
     * @param user     当前用户
     * @return 文件分页列表
     */
    Page<SysFile> getFileList(int page, int pageSize, User user);

    /**
     * 获取文件详情
     *
     * @param id   文件ID
     * @param user 当前用户
     * @return 文件信息
     */
    SysFile getFileDetail(Long id, User user);

    /**
     * 下载文件
     *
     * @param id       文件ID
     * @param user     当前用户
     * @param ipAddress IP地址
     * @return 文件信息
     */
    SysFile downloadFile(Long id, User user, String ipAddress);

    /**
     * 删除文件
     *
     * @param id   文件ID
     * @param user 当前用户
     */
    void deleteFile(Long id, User user);
}
