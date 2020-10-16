package com.paichi.modules.user.mapper;

import com.paichi.modules.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
public interface UserMapper extends BaseMapper<User> {

    void saveUser(User user);

    User getUserByUserId(String userId);

    User getUserByUserEmail(String userEmail);

    User getUserByUserName(String userName);

    List<User> getUserByUserNameOrEmailOrId(@Param("userId") String userId,
                                            @Param("userName") String userName,
                                            @Param("userEmail") String userEmail);

    void updateUser(User user);
}
