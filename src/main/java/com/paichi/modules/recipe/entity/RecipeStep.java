package com.paichi.modules.recipe.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 食谱步骤表
 * </p>
 *
 * @author llb
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("RECIPE_STEP")
public class RecipeStep implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 食谱步骤表主键
     */
    @TableId("RECIPE_STEP_ID")
    private String recipeStepId;

    /**
     * 食谱表主键
     */
    @TableField("RECIPE_ID")
    private String recipeId;

    /**
     * 步骤内容(每一步的描述)
     */
    @TableField("STEP_CONTENT")
    private String stepContent;

    /**
     * 每一个步骤的图片
     */
    @TableField("STEP_IMG")
    private String stepImg;

    /**
     * 食谱步骤（1 2 3 4 5）查询时可以根据此字段排序
     */
    @TableField("STEP_NUMBER")
    private Integer stepNumber;


}
