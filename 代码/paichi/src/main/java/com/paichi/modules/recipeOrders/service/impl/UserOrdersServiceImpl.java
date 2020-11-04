package com.paichi.modules.recipeOrders.service.impl;

import com.paichi.common.util.SnowflakeUtil;
import com.paichi.modules.recipeOrders.entity.OrdersRecipe;
import com.paichi.modules.recipeOrders.entity.UserOrders;
import com.paichi.modules.recipeOrders.mapper.OrdersRecipeMapper;
import com.paichi.modules.recipeOrders.mapper.UserOrdersMapper;
import com.paichi.modules.recipeOrders.service.IUserOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paichi.modules.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 用户食谱(收藏)菜单 服务实现类
 * </p>
 *
 * @author liulebin
 * @since 2020-11-04
 */
@Service
public class UserOrdersServiceImpl extends ServiceImpl<UserOrdersMapper, UserOrders> implements IUserOrdersService {

    private static final Logger log = LoggerFactory.getLogger(UserOrdersServiceImpl.class);

    @Autowired
    private UserOrdersMapper userOrdersMapper;
    @Autowired
    private OrdersRecipeMapper ordersRecipeMapper;

    /**
     * 添加收藏
     * 添加事务，便于失败回滚操作，避免产生无用的数据
     * @param userId   用户主键
     * @param recipeId 食谱主键
     */
    @Override
    @Transactional(rollbackFor=Exception.class) //指定回滚,遇到异常Exception时回滚
    public void addCollection(String userId, String recipeId) {
        OrdersRecipe ordersRecipe = new OrdersRecipe();
        // 首先判断是否存在该用户的菜单
        UserOrders userOrder = userOrdersMapper.getUserOrder(userId);
        if (userOrder != null) {
            ordersRecipe.setUserOrdersId(userOrder.getUserOrdersId());
            ordersRecipe.setRecipeId(recipeId);

            Integer integer = ordersRecipeMapper.addRecipeToOrder(ordersRecipe);
        } else {
            // 没有该用户的菜单，首先创建菜单，再添加收藏
            // 补全数据
            try {
                userOrder = new UserOrders();
                userOrder.setUserOrdersId(SnowflakeUtil.getSnowflakeId());
                userOrder.setUserId(userId);
                userOrder.setUserOrdersTitle("如果你不知道做什么，你就做饭。");
                userOrder.setUserOrdersContent("暂无简介");
                userOrder.setCreateTime(new Date());
                userOrder.setLastUpdateDate(new Date());

                userOrdersMapper.insertUserOrder(userOrder);

                // 补全数据
                ordersRecipe.setUserOrdersId(userOrder.getUserOrdersId());
                ordersRecipe.setRecipeId(recipeId);
                Integer integer = ordersRecipeMapper.addRecipeToOrder(ordersRecipe);
                System.out.println(integer);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("添加收藏失败，数据回滚: {}", e);
            }
        }
    }
}
