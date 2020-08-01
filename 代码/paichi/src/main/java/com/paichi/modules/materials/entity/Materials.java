package com.paichi.modules.materials.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用料表（Materials）
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("MATERIALS")
public class Materials implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用料表主键
     */
    @TableId("MATERIALS_ID")
    private String materialsId;

    /**
     * 食谱表主键
     */
    @TableField("RECIPE_ID")
    private String recipeId;

    /**
     * 用料名称
     */
    @TableField("MAIN_MATERIALS")
    private String mainMaterials;

    /**
     * 用料数量
     */
    @TableField("MAIN_NUMBER")
    private String mainNumber;

    /**
     * 用料标志(1.主料。2.辅料)
     */
    @TableField("MATERIALS_FLAG")
    private Integer materialsFlag;


}
