package com.filesystem.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filesystem.dto.UserDto;
import com.filesystem.entity.User;
import com.filesystem.service.UserService;
import com.filesystem.vo.PageInfo;
import com.filesystem.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户控制器
 *
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表（管理员）
     */
    @GetMapping
    public Result<PageInfo<User>> getUserList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("获取用户列表: page={}, pageSize={}", page, pageSize);

        Page<User> userPage = userService.getUserList(page, pageSize);

        PageInfo<User> pageInfo = PageInfo.of(userPage.getTotal(), userPage.getRecords());
        return Result.success(pageInfo);
    }

    /**
     * 添加用户（管理员）
     */
    @PostMapping
    public Result<User> addUser(@Valid @RequestBody UserDto userDto, HttpServletRequest request) {
        log.info("添加用户: {}", userDto.getUsername());

        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        User createdUser = userService.addUser(user);

        log.info("用户添加成功: {}", createdUser.getUsername());
        return Result.success("添加成功", createdUser);
    }

    /**
     * 删除用户（管理员）
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        log.info("删除用户: id={}", id);

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        // 不允许删除自己
        if (currentUser.getId().equals(id)) {
            return Result.error("不能删除当前登录用户");
        }

        userService.deleteUser(id);

        log.info("用户删除成功: id={}", id);
        return Result.success();
    }

    /**
     * 重置用户密码（管理员）
     */
    @PutMapping("/{id}/password")
    public Result<Void> resetUserPassword(
            @PathVariable Long id,
            @RequestBody UserDto userDto,
            HttpServletRequest request) {
        log.info("重置用户密码: id={}", id);

        userService.resetPassword(id, userDto.getNewPassword());

        log.info("用户密码重置成功: id={}", id);
        return Result.success("密码重置成功");
    }
}
