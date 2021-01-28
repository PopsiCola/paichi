package com.paichi.modules.recipeOrders.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paichi.modules.recipe.entity.Recipe;
import com.paichi.modules.user.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户食谱(收藏)菜单
 * </p>
 *
 * @author liulebin
 * @since 2020-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("USER_ORDERS")
public class UserOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户食谱菜单主键
     */
    @TableId("USER_ORDERS_ID")
    private String userOrdersId;

    /**
     * 用户主键
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 食谱菜单标题
     */
    @TableField("USER_ORDERS_TITLE")
    private String userOrdersTitle;

    /**
     * 食谱菜单简介
     */
    @TableField("USER_ORDERS_CONTENT")
    private String userOrdersContent;

    /**
     * 人气/访问量
     */
    @TableField("POPULARITY")
    private Integer popularity;

    /**
     * 点赞数量
     */
    @TableField("LIKES")
    private Integer likes;

    /**
     * 最后修改时间
     */
    @TableField("LAST_UPDATE_DATE")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date lastUpdateDate;

    /**
     * 创建时间/时间戳
     */
    @TableField("CREATE_TIME")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private Date createTime;

    /**
     * 菜单表和食谱表多对多关系
     */
    private List<Recipe> recipes;

    /**
     * 与用户表一对一关联关系
     */
    private User userOrder;

    /**
     * 菜单中食谱的个数，循环获取recipes.size()即可
     */
    private Integer recipesCount;

}
