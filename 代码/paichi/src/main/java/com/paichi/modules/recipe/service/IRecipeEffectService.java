package com.paichi.modules.recipe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.paichi.modules.recipe.entity.Recipe;
import com.paichi.modules.recipe.entity.RecipeEffect;

/**
 * <p>
 * 食谱功效表 服务类
 * </p>
 *
 * @author llb
 * @since 2020-09-20
 */
public interface IRecipeEffectService extends IService<RecipeEffect> {

    void addRecipeEffect(RecipeEffect recipeEffect);

}
