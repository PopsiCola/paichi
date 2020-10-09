package com.paichi.modules.recipe.controller;


import com.paichi.common.web.Message;
import com.paichi.modules.recipe.entity.Recipe;
import com.paichi.modules.recipe.service.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 食谱表 前端控制器
 * </p>
 *
 * @author llb
 * @since 2020-09-20
 */
@RestController
@RequestMapping("/recipe")
public class RecipeController {

    @Autowired
    private IRecipeService recipeService;

    /**
     * 主页热点菜单Swiper数据
     * TODO：考虑放到Redis缓存中，由于没有连接评论表，暂时无法通过人气来查询，后续需要修改SQL
     * 热点菜单类别(每个类别只取前7条)：
     *          1.每小时最热菜谱：每小时0分0秒准时刷新，数据为上一个小时的数据
     *          2.今日最热菜谱：每天0时0分0秒准时刷新，数据为昨天0点0分0秒-24点59分59秒的数据
     *          4.七天最热菜谱：最近一周，从周一到周日七天的数据
     *          5.最新最热菜谱：最新发布菜谱的数据
     * @param newsType  热点菜单类别
     * @return
     */
    @RequestMapping(value = "/index_more_news", method = RequestMethod.GET)
    @ResponseBody
    public Message indexMoreNews(String newsType) {

        Message message = new Message();

        Map<String, Object> dataMap = new HashMap<>();
        List<Map> recipeList = null;

        switch (newsType) {
            //每小时最热菜谱
            case "1":
                recipeList = recipeService.queryHotRecipeOfHour();
                dataMap.put("title", "每小时最热菜谱");
                break;
            //今日最热菜谱
            case "2":
                recipeList = recipeService.queryHotRecipeOfDay();
                dataMap.put("title", "今日最受欢迎菜谱");
                break;
            //最近一周菜谱
            case "4":
                recipeList = recipeService.queryHotRecipeOfWeek();
                dataMap.put("title", "一周热门菜谱推荐");
                break;
            //最新菜谱
            default:
                recipeList = recipeService.queryHotRecipeOfNow();
                dataMap.put("title", "最新菜谱");
                break;
        }
        dataMap.put("recipeList", recipeList);

        message.setCode(200);
        message.setData(dataMap);
        message.setMsg("查询成功");
        return message;
    }
}

