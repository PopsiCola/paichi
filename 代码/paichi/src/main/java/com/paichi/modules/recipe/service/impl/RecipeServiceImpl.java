package com.paichi.modules.recipe.service.impl;

import com.paichi.modules.recipe.entity.Recipe;
import com.paichi.modules.recipe.mapper.RecipeMapper;
import com.paichi.modules.recipe.service.IRecipeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 食谱表 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-09-20
 */
@Service
public class RecipeServiceImpl extends ServiceImpl<RecipeMapper, Recipe> implements IRecipeService {

    @Autowired
    private RecipeMapper recipeMapper;

    /**
     * 保存食谱
     * @param recipe
     */
    @Override
    public void addRecipe(Recipe recipe) {
        recipeMapper.saveRecipe(recipe);
    }
}
