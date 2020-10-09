package com.paichi.modules.recipe.mapper;

import com.paichi.modules.recipe.entity.Recipe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

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

    /**
     * 每日最热菜谱
     * @return
     */
    List<Map> queryHotRecipeOfDay(String fastDFSPath);

    /**
     * 每小时最热菜谱
     * @return
     */
    List<Map> queryHotRecipeOfHour(String fastDFSPath);

    /**
     * 每周最热菜谱
     * @return
     */
    List<Map> queryHotRecipeOfWeek(String fastDFSPath);

    /**
     * 最新菜谱
     * @return
     */
    List<Map> queryHotRecipeOfNow(String fastDFSPath);
}
