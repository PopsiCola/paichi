package com.paichi;

import com.paichi.common.util.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author liulebin
 * @Date 2020/9/7 14:43
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void testGet() {
        //Object o = redisUtils.get("email:liulebinn@163.com:code");
        Object o = redisUtils.get("info:8fb8253a51d80653d2e7ae001f258ab9:user");
        System.out.println(o);
    }

    @Test
    public void test01() throws InterruptedException {
        boolean set = redisUtils.set("111111", "111111111111");

        Object o = redisUtils.get("111111");
        System.out.println("值：" + o);
        System.out.println("是否存在：" + redisUtils.exists("111111"));
        System.out.println("过期时间：" + redisUtils.getExpire("111111"));
        Thread.sleep(10 * 1000);
        System.out.println("值：" + redisUtils.get("111111"));
        System.out.println("是否存在：" + redisUtils.exists("111111"));
        System.out.println("过期时间：" + redisUtils.getExpire("111111"));
    }

}
