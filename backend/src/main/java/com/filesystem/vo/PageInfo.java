package com.filesystem.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果对象
 *
 */
@Data
public class PageInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页数据列表
     */
    private List<T> list;

    public PageInfo() {
    }

    public PageInfo(Long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    /**
     * 创建分页结果
     */
    public static <T> PageInfo<T> of(Long total, List<T> list) {
        return new PageInfo<>(total, list);
    }
}
