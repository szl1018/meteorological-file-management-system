package com.filesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filesystem.entity.SysFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 文件Mapper接口
 *
 */
@Mapper
public interface FileMapper extends BaseMapper<SysFile> {

    /**
     * 分页查询用户的文件
     *
     * @param page       分页对象
     * @param uploadedBy 上传用户ID（管理员查询时传null）
     * @return 文件列表
     */
    IPage<SysFile> selectFilePage(Page<SysFile> page, @Param("uploadedBy") Long uploadedBy);
}
