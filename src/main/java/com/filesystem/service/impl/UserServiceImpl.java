package com.filesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filesystem.entity.User;
import com.filesystem.exception.BusinessException;
import com.filesystem.mapper.UserMapper;
import com.filesystem.service.LogService;
import com.filesystem.service.UserService;
import com.filesystem.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 *
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LogService logService;

    @Override
    public Page<User> getUserList(int page, int pageSize) {
        Page<User> pageParam = new Page<>(page, pageSize);
        return userMapper.selectPage(pageParam, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User addUser(User user) {
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(wrapper);
        if (count > 0) {
            throw new BusinessException("用户名已存在");
        }

        // 加密密码
        user.setPassword(PasswordUtil.encode(user.getPassword()));

        // 设置创建时间
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 生成ID（使用序列或雪花算法，这里简单处理）
        Long maxId = userMapper.selectCount(null);
        user.setId(maxId + 1);

        userMapper.insert(user);

        // 清除密码后返回
        user.setPassword(null);

        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        userMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long id, String newPassword) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 加密新密码
        String encodedPassword = PasswordUtil.encode(newPassword);

        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setPassword(encodedPassword);
        updateUser.setUpdatedAt(LocalDateTime.now());

        userMapper.updateById(updateUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 验证旧密码
        if (!PasswordUtil.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("原密码错误");
        }

        // 加密新密码
        String encodedPassword = PasswordUtil.encode(newPassword);

        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setPassword(encodedPassword);
        updateUser.setUpdatedAt(LocalDateTime.now());

        userMapper.updateById(updateUser);

        // 记录日志
        logService.recordPasswordChange(userId, user.getUsername());
    }

    @Override
    public User getById(Long id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return user;
    }
}
