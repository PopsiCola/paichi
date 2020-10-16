package com.paichi.modules.user.service;

import com.paichi.common.web.Message;
import com.paichi.modules.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
public interface IUserService extends IService<User> {

    /**
     * 保存用户
     * @param user 用户实体类
     * @return
     */
    void saveUser(User user);

    /**
     * 根据邮箱查询用户
     * @param userEmail 用户邮箱
     * @return
     */
    User getUserByEmail(String userEmail);

    /**
     * 根据用户id查询用户
     * @param userId 用户唯一主键
     * @return
     */
    User getUserByUserId(String userId);

    /**
     * 根据用户账号查询用户
     * @param userName 用户账号
     * @return
     */
    User getUserByUserName(String userName);

    /**
     * 查询账户和邮箱是否存在
     * @param userName  用户账号
     * @param email     邮箱
     * @return          message
     */
    Message emailOrNameExist(String userName, String email);

    /**
     * 通过账号、邮箱、id查找用户
     * @param id        用户唯一主键
     * @param userName  账号
     * @param email     邮箱
     * @return
     */
    List<User> getUserByUserNameOrEmailOrId(String id, String userName, String email);

    /**
     * 修改密码
     * @param user
     */
    void updatePassword(User user);
}
