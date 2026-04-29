package com.filesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.filesystem.dto.LoginRequest;
import com.filesystem.entity.User;
import com.filesystem.enums.UserRole;
import com.filesystem.exception.BusinessException;
import com.filesystem.mapper.UserMapper;
import com.filesystem.service.AuthService;
import com.filesystem.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 认证服务实现类
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(LoginRequest request) {
        // 查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, request.getUsername());
        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 验证密码
        if (!PasswordUtil.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 清除密码字段后返回
        user.setPassword(null);

        return user;
    }

    @Override
    public void logout() {
        // Session已在Controller中销毁
        log.info("用户已退出登录");
    }

    @Override
    public User getCurrentUser() {
        // 从Session中获取，Controller已处理
        throw new BusinessException("未登录");
    }
}
