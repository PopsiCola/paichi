package com.paichi.modules.recipe.service;

import com.paichi.modules.recipe.entity.Recipe;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 食谱表 服务类
 * </p>
 *
 * @author llb
 * @since 2020-09-20
 */
public interface IRecipeService extends IService<Recipe> {

    void addRecipe(Recipe recipe);

}
