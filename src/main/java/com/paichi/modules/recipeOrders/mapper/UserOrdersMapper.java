package com.paichi.modules.recipeOrders.mapper;

import com.paichi.modules.recipeOrders.entity.OrdersRecipe;
import com.paichi.modules.recipeOrders.entity.UserOrders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 查看用户是否已经收藏过该食谱
     * @param userId   用户id
     * @param recipeId 食谱id
     * @return
     */
    OrdersRecipe getUserCollectionByRecipeId(@Param("userId") String userId,
                                             @Param("recipeId") String recipeId);

    /**
     * 移除收藏（取消收藏）
     * @param ordersRecipeId 菜单食谱中间表id
     */
    void removeRecipeFromCollections(Integer ordersRecipeId);

    /**
     * 查看用户菜单详情信息（包含做法、工艺、上传用户等详细信息）
     * @param userId
     * @return
     */
    UserOrders getOrderDetailByUserId(String userId);
}
