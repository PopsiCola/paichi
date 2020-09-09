package com.paichi.modules.user.service.impl;

import com.paichi.common.web.Message;
import com.paichi.modules.user.entity.User;
import com.paichi.modules.user.mapper.UserMapper;
import com.paichi.modules.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 保存用户
     * @param user 用户实体类
     * @return
     */
    @Override
    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

    /**
     * 根据邮箱查询用户
     * @param userEmail 用户邮箱
     * @return
     */
    @Override
    public User getUserByEmail(String userEmail) {
        return userMapper.getUserByUserEmail(userEmail);
    }

    /**
     * 根据用户id查询用户
     * @param userId 用户唯一主键
     * @return
     */
    @Override
    public User getUserByUserId(String userId) {
        return userMapper.getUserByUserId(userId);
    }

    /**
     * 根据用户账号查询用户
     * @param userName 用户账号
     * @return
     */
    @Override
    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    /**
     * 查询账户和邮箱是否存在
     * @param userName  用户账号
     * @param email     邮箱
     * @return          message
     */
    @Override
    public Message emailOrNameExist(String userName, String email) {
        Message message = new Message();
        message.setCode(0);

        User existUser;
        existUser = userMapper.getUserByUserName(userName);
        if (existUser != null) {
            message.setCode(1);
            message.setMsg("该昵称已被注册");
        }
        existUser = userMapper.getUserByUserEmail(email);
        if (existUser != null) {
            message.setCode(1);
            message.setMsg("该邮箱已被注册");
        }
        return message;
    }

    /**
     * 通过账号、邮箱、id查找用户
     * @param id        用户唯一主键
     * @param userName  账号
     * @param email     邮箱
     * @return
     */
    @Override
    public List<User> getUserByUserNameOrEmailOrId(String id, String userName, String email) {
        return userMapper.getUserByUserNameOrEmailOrId(id, userName, email);
    }


}
