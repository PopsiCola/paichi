package com.paichi.modules.recipe.mapper;

import com.paichi.common.web.Page;
import com.paichi.common.web.Term;
import com.paichi.modules.recipe.entity.Recipe;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.paichi.modules.user.entity.User;
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
     * @param page       分页
     * @param term         条件筛选
     * @param fastDFSPath   图片保存服务器地址以及端口号
     * @return
     */
    List<Map> queryRecipeOfSearch(@Param("recipeType") int recipeType,
                                  Page page,
                                  Term term,
                                  @Param("fastDFSPath") String fastDFSPath);

    /**
     * 菜谱条件查询数量
     * @param term         条件筛选
     * @return
     */
    Integer queryRecipeOfSearchCount(Term term);

    /**
     * 菜谱总数
     * @return
     */
    Integer recipeCount();

    /**
     * 美食菜单列表
     * @param newOrHot
     * @return
     */
    List<User> queryRecipeOrders(@Param("newOrHot") int newOrHot, @Param("fastDFSPath") String fastDFSPath);

    /**
     * 美食菜单总数
     * @return
     */
    List<User> recipeOrdersCount();

}
