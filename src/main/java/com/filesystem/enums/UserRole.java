package com.filesystem.enums;

/**
 * 用户角色枚举
 *
 */
public enum UserRole {

    /**
     * 管理员
     */
    ADMIN("ADMIN", "管理员"),

    /**
     * 普通用户
     */
    USER("USER", "普通用户");

    private final String code;
    private final String desc;

    UserRole(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据code获取枚举
     */
    public static UserRole of(String code) {
        for (UserRole role : values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        return null;
    }
}
