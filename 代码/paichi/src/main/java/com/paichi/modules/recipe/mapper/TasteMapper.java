package com.paichi.modules.recipe.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paichi.modules.recipe.entity.Taste;

import java.util.List;

/**
 * <p>
 * 口味表 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-08-01
 */
public interface TasteMapper extends BaseMapper<Taste> {

    Taste getTaste(String tasteName);

    Integer saveTaste(Taste taste);

    List<Taste> queryTaste();
}
