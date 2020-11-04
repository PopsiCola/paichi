package com.paichi.modules.recipeOrders.service.impl;

import com.paichi.modules.recipeOrders.entity.UserOrders;
import com.paichi.modules.recipeOrders.mapper.UserOrdersMapper;
import com.paichi.modules.recipeOrders.service.IUserOrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
