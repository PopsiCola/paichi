package com.paichi.modules.recipeOrders.service.impl;

import com.paichi.modules.recipeOrders.entity.OrdersRecipe;
import com.paichi.modules.recipeOrders.mapper.OrdersRecipeMapper;
import com.paichi.modules.recipeOrders.service.IOrdersRecipeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单食谱表。用户菜单和食谱表的中间表 服务实现类
 * </p>
 *
 * @author liulebin
 * @since 2020-11-04
 */
@Service
public class OrdersRecipeServiceImpl extends ServiceImpl<OrdersRecipeMapper, OrdersRecipe> implements IOrdersRecipeService {

}
