package com.paichi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

/**
 * @Author liulebin
 * @Date 2020/9/9 16:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestMD5 {

    @Test
    public void test1() {
        String s = DigestUtils.md5DigestAsHex("123456".getBytes());
        String s2 = DigestUtils.md5DigestAsHex("123456".getBytes());

        System.out.println(s);
        System.out.println(s2);

        System.out.println(s.equals(s2));
    }

    @Test
    public void test2() {
        for (int i = 0; i < 1000; i++) {
            String s = DigestUtils.md5DigestAsHex(String.valueOf(i).getBytes());
            System.out.println(s);
        }
    }
}
