package com.filesystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filesystem.entity.OperationLog;
import com.filesystem.service.LogService;
import com.filesystem.vo.PageInfo;
import com.filesystem.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志控制器（仅管理员）
 */
@Slf4j
@RestController
@RequestMapping("/logs")
public class LogController {

    @Autowired
    private LogService logService;

    /**
     * 获取操作日志列表（管理员）
     */
    @GetMapping
    public Result<PageInfo<OperationLog>> getLogList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {

        log.info("获取操作日志: page={}, pageSize={}, userId={}, action={}, startDate={}, endDate={}",
                page, pageSize, userId, action, startDate, endDate);

        Page<OperationLog> logPage = logService.getLogList(page, pageSize, userId, action, startDate, endDate);

        PageInfo<OperationLog> pageInfo = PageInfo.of(logPage.getTotal(), logPage.getRecords());
        return Result.success(pageInfo);
    }
}
