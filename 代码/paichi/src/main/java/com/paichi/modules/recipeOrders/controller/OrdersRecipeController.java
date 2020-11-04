package com.paichi.modules.recipeOrders.controller;


import com.paichi.common.util.RedisUtils;
import com.paichi.common.web.Message;
import com.paichi.modules.recipeOrders.service.IOrdersRecipeService;
import com.paichi.modules.recipeOrders.service.IUserOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 菜单食谱表。用户菜单和食谱表的中间表 前端控制器
 * </p>
 * @author liulebin
 * @since 2020-11-04
 */
@RestController
@RequestMapping("/recipeOrders")
public class OrdersRecipeController {

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
                userOrdersService.addCollection(userId, recipeId);
                message.setCode(0);
                message.setMsg("收藏成功");
            } catch (Exception e) {
                message.setCode(1);
                message.setMsg("收藏失败：" + e);
            }
        } else {
            // 没有登录在线
            message.setCode(1);
            message.setMsg("没有登录，请先去登录");
            return message;
        }

        return message;
    }
}

