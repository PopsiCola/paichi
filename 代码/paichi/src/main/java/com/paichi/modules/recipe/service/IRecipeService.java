package com.paichi.modules.recipe.service;

import com.paichi.modules.recipe.entity.Recipe;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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

    List<Map> queryHotRecipeOfDay();

    List<Map> queryHotRecipeOfHour();

    List<Map> queryHotRecipeOfWeek();

    List<Map> queryHotRecipeOfNow();

    Recipe getRecipe(String recipeId);

    Integer getRecipeCount();

    List<Map> queryRecipeOfSearch(int recipeType, int current, int limit);
}
