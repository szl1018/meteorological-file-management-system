package com.filesystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 修改密码请求DTO
 *
 */
@Data
public class PasswordChangeRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 原密码
     */
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
