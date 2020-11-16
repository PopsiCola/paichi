package com.paichi.modules.recipeOrders.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单食谱表。用户菜单和食谱表的中间表
 * </p>
 *
 * @author liulebin
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ORDERS_RECIPE")
public class OrdersRecipe implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单食谱表主键
     */
    @TableId("ORDERS_RECIPE_ID")
    private Integer ordersRecipeId;

    /**
     * 用户菜单表主键
     */
    @TableField("USER_ORDERS_ID")
    private String userOrdersId;

    /**
     * 食谱表主键
     */
    @TableField("RECIPE_ID")
    private String recipeId;


}
