package com.filesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志实体类
 *
 */
@Data
@TableName("SYS_OPERATION_LOGS")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 操作用户名
     */
    private String username;

    /**
     * 操作类型：upload/download/delete/password_change
     */
    private String action;

    /**
     * 关联文件ID
     */
    private Long fileId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 操作IP地址
     */
    private String ipAddress;

    /**
     * 操作时间
     */
    private LocalDateTime createdAt;
}
