package com.paichi.modules.recipeOrders.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

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
    private Date lastUpdateDate;

    /**
     * 创建时间/时间戳
     */
    @TableField("CREATE_TIME")
    private Date createTime;


}
