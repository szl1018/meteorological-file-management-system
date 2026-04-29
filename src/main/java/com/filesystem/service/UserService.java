package com.filesystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.filesystem.entity.User;

/**
 * 用户服务接口
 *
 */
public interface UserService {

    /**
     * 获取用户列表
     *
     * @param page      页码
     * @param pageSize 每页数量
     * @return 用户分页列表
     */
    Page<User> getUserList(int page, int pageSize);

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 创建的用户
     */
    User addUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 重置用户密码
     *
     * @param id          用户ID
     * @param newPassword 新密码
     */
    void resetPassword(Long id, String newPassword);

    /**
     * 修改密码
     *
     * @param userId     用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 根据ID获取用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User getById(Long id);
}
