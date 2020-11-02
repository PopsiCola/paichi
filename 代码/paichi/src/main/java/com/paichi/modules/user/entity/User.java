package com.paichi.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.paichi.modules.recipe.entity.Recipe;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键
     */
    @TableId("USER_ID")
    private String userId;

    /**
     * 用户昵称(用户昵称不能为空，用户注册没有填写昵称时，自动生成以 PaiChi_+随机字符数据 生成的随机名)
     */
    @TableField("USER_NAME")
    private String userName;

    /**
     * 密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * 用户头像地址
     */
    @TableField("USER_ICON")
    private String userIcon;

    /**
     * 用户邮箱
     */
    @TableField("USER_EMAIL")
    private String userEmail;

    /**
     * 性别。1：男  0：女
     */
    @TableField("USER_SEX")
    private Integer userSex;

    /**
     * 用户简介
     */
    @TableField("USER_INTRODUCTION")
    private String userIntroduction;

    /**
     * 出生年月
     */
    @TableField("BIRTH")
    private Date birth;

    /**
     * 注册时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 与Recipe表一对多关联关系
     */
    private List<Recipe> recipes;

    /**
     * 用户发布菜谱的数量
     */
    private Integer ordersCount;
}
