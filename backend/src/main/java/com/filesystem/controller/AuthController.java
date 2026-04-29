package com.filesystem.controller;

import com.filesystem.dto.LoginRequest;
import com.filesystem.entity.User;
import com.filesystem.service.AuthService;
import com.filesystem.util.IpUtil;
import com.filesystem.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 认证控制器
 *
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<User> login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        log.info("用户登录: {}, IP: {}", request.getUsername(), IpUtil.getIpAddress(httpRequest));

        User user = authService.login(request);

        // 将用户信息存入Session
        HttpSession session = httpRequest.getSession(true);
        session.setAttribute("user", user);

        log.info("用户登录成功: {}, SessionId: {}", user.getUsername(), session.getId());
        return Result.success(user);
    }

    /**
     * 用户退出
     */
    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            log.info("用户退出: {}", user != null ? user.getUsername() : "unknown");
            session.invalidate();
        }
        return Result.success();
    }

    /**
     * 获取当前登录用户信息
     */
    @GetMapping("/current")
    public Result<User> getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return Result.unauthorized();
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return Result.unauthorized();
        }

        return Result.success(user);
    }
}
