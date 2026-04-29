package com.filesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件实体类
 *
 */
@Data
@TableName("SYS_FILES")
public class SysFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 文件MIME类型
     */
    private String mimeType;

    /**
     * 文件内容（BLOB）
     */
    private byte[] fileContent;

    /**
     * 上传用户ID
     */
    private Long uploadedBy;

    /**
     * 上传时间
     */
    private LocalDateTime createdAt;
}
