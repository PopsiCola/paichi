package com.paichi.modules.craft.mapper;

import com.paichi.modules.craft.entity.Craft;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 食谱工艺(食谱做法)表 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
public interface CraftMapper extends BaseMapper<Craft> {

    List<Craft> queryCraft();

    Craft getCraft(String craftName);

    Integer saveCraft(Craft craft);
}
