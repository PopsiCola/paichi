package com.paichi.modules.user.service.impl;

import com.paichi.modules.user.entity.User;
import com.paichi.modules.user.mapper.UserMapper;
import com.paichi.modules.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
