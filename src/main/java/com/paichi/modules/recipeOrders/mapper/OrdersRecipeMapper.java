package com.paichi.modules.recipeOrders.mapper;

import com.paichi.modules.recipeOrders.entity.OrdersRecipe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 菜单食谱表。用户菜单和食谱表的中间表 Mapper 接口
 * </p>
 *
 * @author liulebin
 * @since 2020-11-04
 */
public interface OrdersRecipeMapper extends BaseMapper<OrdersRecipe> {

    /**
     * 添加收藏
     */
    Integer addRecipeToOrder(OrdersRecipe ordersRecipe);
}
