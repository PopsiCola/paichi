package com.paichi.modules.recipeOrders.mapper;

import com.paichi.modules.recipeOrders.entity.UserOrders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户食谱(收藏)菜单 Mapper 接口
 * </p>
 *
 * @author liulebin
 * @since 2020-11-04
 */
public interface UserOrdersMapper extends BaseMapper<UserOrders> {

    /**
     * 查询用户菜单
     * @param userId 用户主键
     * @return
     */
    UserOrders getUserOrder(String userId);

    /**
     * 创建用户订单
     * @param userOrders
     */
    void insertUserOrder(UserOrders userOrders);
}
