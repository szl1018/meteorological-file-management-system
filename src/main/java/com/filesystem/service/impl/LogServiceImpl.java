package com.filesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filesystem.entity.OperationLog;
import com.filesystem.enums.LogAction;
import com.filesystem.mapper.LogMapper;
import com.filesystem.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 操作日志服务实现类
 *
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordLog(Long userId, String username, LogAction action,
                          Long fileId, String fileName, String ipAddress) {
        // 生成ID
        Long maxId = logMapper.selectCount(null);
        Long logId = maxId + 1;

        OperationLog operationLog = new OperationLog();
        operationLog.setId(logId);
        operationLog.setUserId(userId);
        operationLog.setUsername(username);
        operationLog.setAction(action.getCode());
        operationLog.setFileId(fileId);
        operationLog.setFileName(fileName);
        operationLog.setIpAddress(ipAddress);
        operationLog.setCreatedAt(LocalDateTime.now());

        logMapper.insert(operationLog);
    }

    @Override
    public void recordPasswordChange(Long userId, String username) {
        recordLog(userId, username, LogAction.PASSWORD_CHANGE, null, null, null);
    }

    @Override
    public Page<OperationLog> getLogList(int page, int pageSize, Long userId,
                                         String action, String startDate, String endDate) {
        Page<OperationLog> pageParam = new Page<>(page, pageSize);

        return logMapper.selectLogPage(pageParam, userId, action, startDate, endDate);
    }
}
