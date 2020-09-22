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
 * 功效表，与食谱表多对多关系
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
    private Integer effectId;

    /**
     * 功效名称
     */
    @TableField("EFFECT_NAME")
    private String effectName;


}
