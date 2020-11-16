package com.paichi.modules.craft.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 食谱工艺(食谱做法)表
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("CRAFT")
public class Craft implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 食谱工艺表主键
     */
    @TableId(value = "CRAFT_ID", type = IdType.AUTO)
    private Integer craftId;

    /**
     * 食谱工艺名称
     */
    @TableField("CRAFT_NAME")
    private String craftName;


}
