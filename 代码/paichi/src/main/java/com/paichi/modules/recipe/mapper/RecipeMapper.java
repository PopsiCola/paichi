package com.paichi.modules.recipe.mapper;

import com.paichi.modules.recipe.entity.Recipe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 菜谱详情以及做法
     * @param recipeId  菜谱id
     * @return
     */
    Recipe getRecipeDetailByRecipeId(String recipeId, String fastDFSPath);

    /**
     * 菜谱搜索
     * @param recipeType    查询菜谱，1：最新，根据发布时间倒叙查询。2：最热，根据人气正叙查询
     * @param current       当前页
     * @param limit         每页显示条数
     * @param fastDFSPath   图片保存服务器地址以及端口号
     * @return
     */
    List<Map> queryRecipeOfSearch(@Param("recipeType") int recipeType,
                                  @Param("current") int current,
                                  @Param("limit") int limit,
                                  @Param("fastDFSPath") String fastDFSPath);

    /**
     * 菜谱总数
     * @return
     */
    Integer recipeCount();

}
