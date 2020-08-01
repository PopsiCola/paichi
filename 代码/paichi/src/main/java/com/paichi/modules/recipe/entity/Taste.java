package com.paichi.modules.recipe.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 口味表
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("TASTE")
public class Taste implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 口味表主键
     */
    @TableId("TASTE_ID")
    private Double tasteId;

    /**
     * 口味名称
     */
    @TableField("TASTE_NAME")
    private String tasteName;


}
