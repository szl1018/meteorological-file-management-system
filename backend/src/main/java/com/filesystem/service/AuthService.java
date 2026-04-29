package com.filesystem.service;

import com.filesystem.dto.LoginRequest;
import com.filesystem.entity.User;

/**
 * 认证服务接口
 *
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 用户信息
     */
    User login(LoginRequest request);

    /**
     * 用户退出
     */
    void logout();

    /**
     * 获取当前登录用户
     *
     * @return 用户信息
     */
    User getCurrentUser();
}
