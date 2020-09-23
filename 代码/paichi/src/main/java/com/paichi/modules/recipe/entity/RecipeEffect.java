package com.paichi.modules.recipe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 食品功效表
 * @Author liulebin
 * @Date 2020/9/22 20:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("RECIPE_EFFECT")
public class RecipeEffect {

    private static final long serialVersionUID = 1L;

    @TableId("RECIPE_EFFECT_ID")
    private String recipeEffectId;

    @TableField("RECIPE_ID")
    private String recipeId;

    @TableField("EFFECT_ID")
    private Integer effectId;
}
