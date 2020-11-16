package com.paichi;

import com.paichi.common.util.CrawUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author liulebin
 * @Date 2020/9/17 20:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CrawData {

    /**
     * 爬取菜谱，包含做法等信息
     */
    @Test
    public void crawReipe() {
        CrawUtil crawUtil = new CrawUtil();

        List<String> urlList = new ArrayList<>();
        //String meishiUrl = "https://www.meishij.net";
        String pageUrl = "https://www.meishij.net/list.php?lm=270&page=1";
        urlList.add(pageUrl);

        crawUtil.saveRecipeData(pageUrl);

    }


}
