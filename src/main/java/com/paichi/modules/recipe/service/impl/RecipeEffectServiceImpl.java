package com.paichi.modules.recipe.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paichi.modules.recipe.entity.Recipe;
import com.paichi.modules.recipe.entity.RecipeEffect;
import com.paichi.modules.recipe.mapper.RecipeEffectMapper;
import com.paichi.modules.recipe.mapper.RecipeMapper;
import com.paichi.modules.recipe.service.IRecipeEffectService;
import com.paichi.modules.recipe.service.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 食谱功效表 服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-09-20
 */
@Service
public class RecipeEffectServiceImpl extends ServiceImpl<RecipeEffectMapper, RecipeEffect> implements IRecipeEffectService {

    @Autowired
    private RecipeEffectMapper recipeEffectMapper;

    /**
     * 保存食谱功效中间表
     *
     * @param recipeEffect
     */
    @Override
    public void addRecipeEffect(RecipeEffect recipeEffect) {
        recipeEffectMapper.saveRecipeEffect(recipeEffect);
    }

}