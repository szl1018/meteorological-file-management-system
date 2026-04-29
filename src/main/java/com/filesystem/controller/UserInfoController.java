package com.filesystem.controller;

import com.filesystem.dto.PasswordChangeRequest;
import com.filesystem.entity.User;
import com.filesystem.service.UserService;
import com.filesystem.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 个人中心控制器
 *
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserService userService;

    /**
     * 获取个人信息
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        log.info("获取个人信息: {}", user.getUsername());

        // 从数据库重新查询最新信息
        User userInfo = userService.getById(user.getId());

        return Result.success(userInfo);
    }

    /**
     * 修改自己的密码
     */
    @PutMapping("/password")
    public Result<Void> changePassword(
            @Valid @RequestBody PasswordChangeRequest request,
            HttpServletRequest httpRequest) {

        HttpSession session = httpRequest.getSession();
        User user = (User) session.getAttribute("user");

        log.info("用户 {} 修改密码", user.getUsername());

        userService.changePassword(user.getId(), request.getOldPassword(), request.getNewPassword());

        // 修改成功后清除Session，需要重新登录
        session.invalidate();

        log.info("用户 {} 密码修改成功", user.getUsername());
        return Result.success("密码修改成功，请重新登录");
    }
}
