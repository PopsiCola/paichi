package com.paichi.modules.effect.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 功效表
 * </p>
 *
 * @author llb
 * @since 2020-09-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("EFFECT")
public class Effect implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 功效表主键
     */
    @TableId("EFFECT_ID")
    private String effectId;

    /**
     * 食谱表主键
     */
    @TableField("RECIPE_ID")
    private String recipeId;

    /**
     * 功效名称
     */
    @TableField("EFFECT_NAME")
    private String effectName;


}
