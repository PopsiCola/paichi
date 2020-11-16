package com.paichi;

import com.paichi.common.web.Page;
import com.paichi.common.web.Term;
import com.paichi.modules.materials.entity.Materials;
import com.paichi.modules.materials.mapper.MaterialsMapper;
import com.paichi.modules.recipe.entity.Recipe;
import com.paichi.modules.recipe.mapper.RecipeMapper;
import com.paichi.modules.recipeOrders.entity.OrdersRecipe;
import com.paichi.modules.recipeOrders.entity.UserOrders;
import com.paichi.modules.recipeOrders.mapper.OrdersRecipeMapper;
import com.paichi.modules.recipeOrders.mapper.UserOrdersMapper;
import com.paichi.modules.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * @Author liulebin
 * @Date 2020/10/3 11:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestSQL {

    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private MaterialsMapper materialsMapper;
    @Autowired
    private OrdersRecipeMapper ordersRecipeMapper;
    @Autowired
    private UserOrdersMapper userOrdersMapper;

    //文件保存地址
    @Value("${fastDFSPath}")
    private String fastDFSPath;

    @Test
    public void test01() {
        List<Map> maps = recipeMapper.queryHotRecipeOfDay(fastDFSPath);
        maps.forEach(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void zuofa() {
        Recipe recipeZuofa = recipeMapper.getRecipeDetailByRecipeId("1308995796598439936", fastDFSPath);
        System.out.println(recipeZuofa.toString());
    }



    @Test
    public void searchRecipe1() {
        /*List<Map> maps = recipeMapper.queryRecipeOfSearch(0, 18, fastDFSPath);
        maps.forEach(str -> {
            System.out.println(str);
        });*/
    }

    @Test
    public void queryMainMaterial() {
        int limit = 0;
        List<Map> maps = materialsMapper.queryMainMaterial();
        maps.forEach(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void queryRecipeOrders() {
        Page page = new Page();
        page.setCurrent(1);
        page.setLimit(10);

        List<User> users = recipeMapper.queryRecipeOrders(1, "");

        users.forEach(user -> {
            System.out.println(user);
        });
    }

    @Test
    public void test00() {
        List list = new ArrayList();
        list.add("1");
        list.add("2");
        list = list.subList(0, 2);
        list.forEach(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void testCollection() {
        //Integer integer = ordersRecipeMapper.addRecipeToOrder();
    }

    @Test
    public void testGetOrderDetailByUserId() {
        UserOrders userOrders = userOrdersMapper.getOrderDetailByUserId("1303607221849059328");
        System.out.println(userOrders);
    }

}
