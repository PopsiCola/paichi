package com.paichi.modules.recipe.service.impl;

import com.paichi.common.web.Page;
import com.paichi.common.web.Term;
import com.paichi.modules.recipe.entity.Recipe;
import com.paichi.modules.recipe.mapper.RecipeMapper;
import com.paichi.modules.recipe.service.IRecipeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.paichi.modules.recipeOrders.entity.UserOrders;
import com.paichi.modules.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    //文件保存地址
    @Value("${fastDFSPath}")
    private String fastDFSPath;
    @Autowired
    private HttpServletRequest request;

    /**
     * 保存食谱
     * @param recipe
     */
    @Override
    public void addRecipe(Recipe recipe) {
        recipeMapper.saveRecipe(recipe);
    }

    /**
     * 每天最热菜谱
     * @return
     */
    @Override
    public List<Map> queryHotRecipeOfDay() {
        return recipeMapper.queryHotRecipeOfDay(fastDFSPath);
    }

    /**
     * 每小时最热菜谱
     * @return
     */
    @Override
    public List<Map> queryHotRecipeOfHour() {
        return recipeMapper.queryHotRecipeOfHour(fastDFSPath);
    }

    /**
     * 每周最热菜谱
     * @return
     */
    @Override
    public List<Map> queryHotRecipeOfWeek() {
        return recipeMapper.queryHotRecipeOfWeek(fastDFSPath);
    }

    /**
     * 最新最热菜谱
     * @return
     */
    @Override
    public List<Map> queryHotRecipeOfNow() {
        return recipeMapper.queryHotRecipeOfNow(fastDFSPath);
    }

    /**
     * 查询菜谱，包含菜谱详细信息，步骤做法以及所有功效
     * @param recipeId  食谱表主键id
     * @return
     */
    @Override
    public Recipe getRecipe(String recipeId) {
        return recipeMapper.getRecipeDetailByRecipeId(recipeId, fastDFSPath);
    }

    /**
     * 获取菜谱总数
     * @return
     */
    @Override
    public Integer getRecipeCount() {
        return recipeMapper.recipeCount();
    }

    /**
     * 条件搜索，分页展示菜谱
     * @param recipeType    查询菜谱，1：最新，根据发布时间倒叙查询。2：最热，根据人气正叙查询
     * @param page          分页
     * @param term          条件筛选
     * @return
     */
    @Override
    public List<Map> queryRecipeOfSearch(int recipeType, Page page, Term term) {
        return recipeMapper.queryRecipeOfSearch(recipeType, page, term, fastDFSPath);
    }

    /**
     * 条件搜索菜谱的数量
     * @param term          条件筛选
     * @return
     */
    @Override
    public Integer queryRecipeOfSearchCount(Term term) {
        return recipeMapper.queryRecipeOfSearchCount(term);
    }

    /**
     * 美食菜单
     * @param newOrHot    查询菜单，1：最新，根据发布时间倒叙查询。2：最热，根据人气正叙查询
     * @param page          分页
     * @return
     */
    @Override
    public List<UserOrders> queryRecipeOrders(int newOrHot, Page page) {
        // TODO:此处使用了一对多分组查询，没有想出如何在sql中分页，想到后修改
        List<UserOrders> orders = recipeMapper.queryRecipeOrders(newOrHot, fastDFSPath);
        // 模拟分页
        orders = orders.subList((page.getCurrent() - 1) * page.getLimit() < orders.size() ? (page.getCurrent() - 1) * page.getLimit() : orders.size() -1,
                page.getCurrent() * page.getLimit() < orders.size() ? page.getCurrent() * page.getLimit() : orders.size());
        return orders;
    }

    /**
     * 美食菜单总数
     * @return
     */
    @Override
    public Integer recipeOrdersCount() {
        return recipeMapper.recipeOrdersCount().size();
    }
}
