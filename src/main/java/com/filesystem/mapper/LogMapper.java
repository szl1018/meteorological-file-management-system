package com.filesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filesystem.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 操作日志Mapper接口
 *
 */
@Mapper
public interface LogMapper extends BaseMapper<OperationLog> {

    /**
     * 分页查询操作日志
     *
     * @param page     分页对象
     * @param userId   用户ID（可选）
     * @param action   操作类型（可选）
     * @param startDate 开始日期（可选）
     * @param endDate   结束日期（可选）
     * @return 日志列表
     */
    IPage<OperationLog> selectLogPage(
            Page<OperationLog> page,
            @Param("userId") Long userId,
            @Param("action") String action,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
}
