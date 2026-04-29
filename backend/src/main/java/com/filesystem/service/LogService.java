package com.filesystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filesystem.entity.OperationLog;
import com.filesystem.enums.LogAction;

/**
 * 操作日志服务接口
 */
public interface LogService {

    /**
     * 记录操作日志
     *
     * @param userId    用户ID
     * @param username  用户名
     * @param action    操作类型
     * @param fileId    文件ID（可选）
     * @param fileName  文件名（可选）
     * @param ipAddress IP地址（可选）
     */
    void recordLog(Long userId, String username, LogAction action,
                   Long fileId, String fileName, String ipAddress);

    /**
     * 记录密码修改日志
     *
     * @param userId   用户ID
     * @param username 用户名
     */
    void recordPasswordChange(Long userId, String username);

    /**
     * 获取操作日志列表
     *
     * @param page      页码
     * @param pageSize  每页数量
     * @param userId    用户ID（可选）
     * @param action    操作类型（可选）
     * @param startDate 开始日期（可选）
     * @param endDate   结束日期（可选）
     * @return 日志分页列表
     */
    Page<OperationLog> getLogList(int page, int pageSize, Long userId,
                                  String action, String startDate, String endDate);
}
