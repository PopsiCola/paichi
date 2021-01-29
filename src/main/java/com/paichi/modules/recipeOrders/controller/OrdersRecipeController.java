package com.paichi.modules.recipeOrders.controller;

import com.paichi.common.util.RedisUtils;
import com.paichi.common.web.Message;
import com.paichi.modules.recipeOrders.entity.OrdersRecipe;
import com.paichi.modules.recipeOrders.entity.UserOrders;
import com.paichi.modules.recipeOrders.service.IUserOrdersService;
import com.paichi.modules.recipeOrders.service.impl.UserOrdersServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 菜单食谱表。用户菜单和食谱表的中间表 前端控制器
 * </p>
 * @author liulebin
 * @since 2020-11-04
 */
@Controller
@RequestMapping("recipeOrders")
public class OrdersRecipeController {

    private static final Logger log = LoggerFactory.getLogger(OrdersRecipeController.class);

    @Autowired
    private IUserOrdersService userOrdersService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 添加到收藏菜单
     * 逻辑：
     * 1.先判断是否已经存在该用户表单，如果不存在，先创建用户菜单再添加食谱
     * 2.如果存在用户表单，则直接在中间表中保存菜谱
     * @param recipeId 食谱主键
     * @param userId   用户主键
     * @return
     */
    @PostMapping("addCollection")
    @ResponseBody
    public Message addCollection(String recipeId, String userId) {
        Message message = new Message();

        // 后端通过redis校验用户是否在线
        boolean exists = redisUtils.exists("info:" + userId + ":user");
        if (exists) {

            try {

                // 添加收藏前需要查询该用户是否已经收藏过该食谱，如果已收藏，则移出收藏菜单
                OrdersRecipe collection = userOrdersService.getUserCollectionByRecipeId(userId, recipeId);
                if (collection != null) {
                    // 取消收藏
                    userOrdersService.removeRecipeFromColletion(collection.getOrdersRecipeId());
                    message.setCode(0);
                    message.setData(false);
                    message.setMsg("取消收藏成功");
                    log.info("取消收藏成功", userId);
                } else {
                    userOrdersService.addCollection(userId, recipeId);
                    message.setCode(0);
                    message.setData(true);
                    message.setMsg("收藏成功");
                    log.info("取消收藏成功", userId);
                }

            } catch (Exception e) {
                message.setCode(1);
                message.setMsg("操作失败：" + e);
                log.info("取消收藏成功：{}", e);
                return message;
            }
        } else {
            // 没有登录在线
            message.setCode(1);
            message.setMsg("没有登录，请先去登录");
        }
        return message;
    }

    /**
     * 查询是否为用户喜欢的食谱
     * @param userId   用户id
     * @param recipeId 食谱id
     * @return
     */
    @PostMapping("isFavorite")
    @ResponseBody
    public Message isFavorite(String userId, String recipeId) {
        Message message = new Message();

        OrdersRecipe isFavorite = userOrdersService.getUserCollectionByRecipeId(userId, recipeId);

        message.setCode(0);
        message.setMsg("查询成功");
        message.setData(isFavorite == null ? false : true);
        return message;
    }

    /**
     * 菜单详情页面
     * @param uid     用户id
     * @return
     */
    @GetMapping("recipeOrderDetail")
    public ModelAndView recipeOrderDetail(String uid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("uid", uid);
        modelAndView.setViewName("/order_list/detail/orders_detail");
        return modelAndView;
    }

    /**
     * 用户菜单详细信息
     * @param userId 用户id
     * @return
     */
    @PostMapping("userOrderDetail")
    @ResponseBody
    public Message userOrderDetail(String userId) {
        Message message = new Message();
        UserOrders userOrder = userOrdersService.getOrderDetailByUserId(userId);
        if (userOrder != null) {
            message.setCode(0);
            message.setData(userOrder);
            message.setMsg("查询成功");
        } else {
            message.setCode(1);
            message.setMsg("用户没有菜单");
        }
        return message;
    }
}

