package com.paichi.modules.user.service;

import com.paichi.modules.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
    void saveUsr(User user);

}
