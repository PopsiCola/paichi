package com.paichi.modules.recipe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paichi.modules.recipe.entity.Recipe;
import com.paichi.modules.recipe.entity.RecipeEffect;

/**
 * <p>
 * 食谱功效表 Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-09-20
 */
public interface RecipeEffectMapper extends BaseMapper<RecipeEffect> {

    /**
     * 保存食谱功效中间表
     * @param
     */
    void saveRecipeEffect(RecipeEffect recipeEffect);
}
