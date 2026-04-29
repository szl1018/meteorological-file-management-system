package com.filesystem.enums;

/**
 * 操作日志类型枚举
 *
 */
public enum LogAction {

    /**
     * 文件上传
     */
    UPLOAD("upload", "文件上传"),

    /**
     * 文件下载
     */
    DOWNLOAD("download", "文件下载"),

    /**
     * 文件删除
     */
    DELETE("delete", "文件删除"),

    /**
     * 密码修改
     */
    PASSWORD_CHANGE("password_change", "密码修改");

    private final String code;
    private final String desc;

    LogAction(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
