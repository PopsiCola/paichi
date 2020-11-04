package com.paichi.modules.recipeOrders.service;

import com.paichi.modules.recipeOrders.entity.UserOrders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户食谱(收藏)菜单 服务类
 * </p>
 *
 * @author liulebin
 * @since 2020-11-04
 */
public interface IUserOrdersService extends IService<UserOrders> {

    void addCollection(String userId, String recipeId);
}
