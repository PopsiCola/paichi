package com.paichi.modules.recipe.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 食谱表
 * </p>
 *
 * @author llb
 * @since 2020-08-01
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
    private Double craftId;

    /**
     * 人数（食用人数）
     */
    @TableField("PEOPLE_NUMBER")
    private Integer peopleNumber;

    /**
     * 口味表主键(麻辣、香甜等口味)
     */
    @TableField("TASTE_ID")
    private Double tasteId;

    /**
     * 烹饪时间
     */
    @TableField("COOK_TIME")
    private LocalDateTime cookTime;

    /**
     * 食谱步骤表
     */
    @TableField("RECIPE_STEP_ID")
    private String recipeStepId;

    /**
     * 上传用户主键
     */
    @TableField("USER_ID")
    private String userId;

    /**
     * 时间戳
     */
    @TableField("TIMESTAMP")
    private LocalDateTime timestamp;


}
