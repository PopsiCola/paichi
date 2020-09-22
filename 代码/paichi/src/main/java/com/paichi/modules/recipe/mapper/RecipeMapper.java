package com.paichi.modules.recipe.mapper;

import com.paichi.modules.recipe.entity.Recipe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 食谱表 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-09-20
 */
public interface RecipeMapper extends BaseMapper<Recipe> {

    /**
     * 保存食谱
     * @param recipe
     */
    void saveRecipe(Recipe recipe);
}
