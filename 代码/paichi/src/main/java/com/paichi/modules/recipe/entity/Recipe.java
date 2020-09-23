package com.paichi.modules.recipe.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.paichi.modules.effect.entity.Effect;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 食谱表
 * </p>
 *
 * @author llb
 * @since 2020-09-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("RECIPE")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 食谱表主键
     */
    @TableId("RECIPE_ID")
    private String recipeId;

    /**
     * 食谱名称
     */
    @TableField("RECIPE_NAME")
    private String recipeName;

    /**
     * 食谱图片
     */
    @TableField("RECIPE_IMG")
    private String recipeImg;

    /**
     * 食谱视频
     */
    @TableField("RECIPE_VIDEO")
    private String recipeVideo;

    /**
     * 工艺表主键(包含炒、煮、烧烤等方法)
     */
    @TableField("CRAFT_ID")
    private Integer craftId;

    /**
     * 口味表主键(麻辣、香甜等口味)
     */
    @TableField("TASTE_ID")
    private Integer tasteId;

    /**
     * 上传用户主键
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 人数（食用人数）
     */
    @TableField("PEOPLE_NUMBER")
    private Integer peopleNumber;

    /**
     * 烹饪时间  按照分钟为单位
     */
    @TableField("COOK_TIME")
    private String cookTime;

    /**
     * 准备时间
     */
    @TableField("PREPARATION_TIME")
    private Integer preparationTime;

    /**
     * 人气
     */
    @TableField("POPULARITY")
    private Integer popularity;

    /**
     * 难度
     */
    @TableField("DIFFICULTY")
    private Integer difficulty;

    /**
     * 简介
     */
    @TableField("INTRODUCTION")
    private String introduction;

    /**
     * 技巧
     */
    @TableField("COOKINGSKILL")
    private String cookingSkill;

    /**
     * 时间戳
     */
    @TableField("TIMESTAMP")
    private Date timestamp;

    //与effect表多对多关联关系
    private List<Effect> effects;
}
