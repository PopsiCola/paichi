package com.paichi.modules.recipe.service.impl;

import com.paichi.modules.recipe.entity.RecipeStep;
import com.paichi.modules.recipe.mapper.RecipeStepMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paichi.modules.recipe.service.IRecipeStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 食谱步骤表 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-09-21
 */
@Service
public class RecipeStepServiceImpl extends ServiceImpl<RecipeStepMapper, RecipeStep> implements IRecipeStepService {

    @Autowired
    private RecipeStepMapper recipeStepMapper;

    /**
     * 保存食谱步骤
     * @param recipeStep
     */
    @Override
    public void addRecipeStep(RecipeStep recipeStep) {
        recipeStepMapper.saveRecipeStep(recipeStep);
    }
}
