package com.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户DTO
 *
 */
@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = {Add.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {Add.class})
    private String password;

    /**
     * 真实姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 角色
     */
    @NotBlank(message = "角色不能为空", groups = {Add.class})
    private String role;

    /**
     * 新密码（用于修改密码）
     */
    private String newPassword;

    /**
     * 分组：新增
     */
    public interface Add {
    }

    /**
     * 分组：更新
     */
    public interface Update {
    }
}
