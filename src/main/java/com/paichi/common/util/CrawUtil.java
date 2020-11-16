package com.paichi.common.util;

import com.paichi.modules.craft.entity.Craft;
import com.paichi.modules.craft.service.ICraftService;
import com.paichi.modules.effect.entity.Effect;
import com.paichi.modules.effect.service.IEffectService;
import com.paichi.modules.materials.entity.Materials;
import com.paichi.modules.materials.service.IMaterialsService;
import com.paichi.modules.recipe.entity.Recipe;
import com.paichi.modules.recipe.entity.RecipeEffect;
import com.paichi.modules.recipe.entity.RecipeStep;
import com.paichi.modules.recipe.entity.Taste;
import com.paichi.modules.recipe.service.IRecipeEffectService;
import com.paichi.modules.recipe.service.IRecipeService;
import com.paichi.modules.recipe.service.IRecipeStepService;
import com.paichi.modules.recipe.service.ITasteService;
import com.paichi.modules.record.entity.FileRecord;
import com.paichi.modules.record.service.IFileRecordService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * 爬虫方法
 * @Author liulebin
 * @Date 2020/9/20 23:08
 */
@Service
public class CrawUtil {

    @Autowired
    private IRecipeService recipeService;
    @Autowired
    private IRecipeStepService recipeStepService;
    @Autowired
    private IMaterialsService materialsService;
    @Autowired
    private ICraftService craftService;
    @Autowired
    private ITasteService tasteService;
    @Autowired
    private IEffectService effectService;
    @Autowired
    private IFileRecordService fileRecordService;
    @Autowired
    private IRecipeEffectService recipeEffectService;
    //静态初使化 一个工具类  这样是为了在spring初使化之前
    private static CrawUtil crawUtil;

    //通过@PostConstruct 和 @PreDestroy 方法 实现初始化和销毁bean之前进行的操作
    @PostConstruct
    public void init() {
        crawUtil = this;
        //初使化时将已静态化的service实例化
        crawUtil.recipeService = this.recipeService;
        crawUtil.recipeStepService = this.recipeStepService;
        crawUtil.materialsService = this.materialsService;
        crawUtil.craftService = this.craftService;
        crawUtil.tasteService = this.tasteService;
        crawUtil.effectService = this.effectService;
        crawUtil.fileRecordService = this.fileRecordService;
        crawUtil.recipeEffectService = this.recipeEffectService;
    }


    public static void saveRecipeData(String url) {
        System.out.println("========爬取页面========" +url);

        String meishiUrl = "https://www.meishij.net";
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        try {
            //执行get请求
            httpClient.executeMethod(getMethod);

            //获取返回的数据
            String text = getMethod.getResponseBodyAsString();
            new CrawUtil().crawData(text);

            //根据请求返回的数据找出下一页
            //<a href="/list.php?lm=270&page=2" class="next">下一页</a>
            Document doc = Jsoup.parse(text);
            Elements select = doc.select("a.next");
            String pageUrl = select.attr("href");



            if (pageUrl == "" || "".equals(pageUrl)) {
                System.out.println("爬取完成");
                return;
            }
            saveRecipeData(meishiUrl + pageUrl);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("发起请求失败");
        }
    }

    /**
     * 爬取页面
     * @param htmlText
     */
    public void crawData(String htmlText) {

        Document doc = Jsoup.parse(htmlText);
        Elements elements = doc.select("div.listtyle1");
        //System.out.println(elements);
        for(Element element : elements) {
            //涉及到的表
            //食谱表
            Recipe recipe = new Recipe();
            //口味表
            Taste tat = new Taste();
            //做法表
            Craft craft = new Craft();

            //详情页面
            String recipeDetail = element.select("a.big").attr("href");
            //爬取菜名
            String recipeName = element.select("img.img").attr("alt");
            //菜品图片
            String recipeImg = element.select("img.img").attr("src");
            //评论
            //人气
            String renqi = element.select("span").text();
            renqi = renqi.substring(renqi.indexOf("论") + 1, renqi.indexOf("人")).trim();
            //分钟数 12步 / 大概10分钟 有的数据小时为单位，有的数据分钟为单位，这里加个判断
            String fenzhong = element.select("li.li1").text();
            String[] split = fenzhong.split("/");
            fenzhong = split[1].trim();

            if (fenzhong.contains("分钟")) {
                fenzhong = fenzhong.replaceAll("[\\u4e00-\\u9fa5]", "").trim();
                if (fenzhong != null && !"".equals(fenzhong)) {
                    recipe.setPreparationTime(Integer.parseInt(fenzhong));
                }
            } else if (fenzhong.contains("小时")){
                fenzhong = fenzhong.replaceAll("[\\u4e00-\\u9fa5]", "").trim();
                if (fenzhong != null && !"".equals(fenzhong)) {
                    recipe.setPreparationTime(Integer.parseInt(fenzhong) * 60);
                }
            } else {
            }


            //做法 烙 / 家常味 包含做法和味道
            String zuofa = element.select("li.li2").text();
            String[] splits = zuofa.split("/");
            zuofa = splits[0].trim();

            //味道
            String taste = splits[1].trim();

            try {
                recipe.setRecipeId(SnowflakeUtil.getSnowflakeId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (recipeImg != "" && !"".equals(recipeImg)) {
                    //保存图片
                    byte[] imagedata = new FileIOStreamUtil().getImageFromNetByUrl(recipeImg);

                    String extName = recipeImg.substring(recipeImg.lastIndexOf(".") + 1);
                    String url = new FastDFSUtils().uploadFile(imagedata, extName);

                    recipeImg = url;

                    FileRecord fileRecord = new FileRecord();
                    fileRecord.setPictureType(1);
                    fileRecord.setUploadTime(new Date());
                    fileRecord.setPictureUrl(url);
                    fileRecord.setDelFlag(1);
                    fileRecord.setUploadUserId("1306523235645465465");

                    crawUtil.fileRecordService.saveFile(fileRecord);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            recipe.setRecipeName(recipeName);
            recipe.setRecipeImg(recipeImg);
//            recipe.setCookTime(fenzhong);
            recipe.setPopularity(Integer.parseInt(renqi));
            recipe.setUserId("1306523235645465465");
            recipe.setTimestamp(new Date());

            //保存口味表和做法表，主键为自增主键
            craft.setCraftName(zuofa);

            tat.setTasteName(taste);

            Integer craftId = crawUtil.craftService.addCraft(craft);
            Integer tasteId = crawUtil.tasteService.addTaste(tat);


            System.out.println(recipe.toString());


            recipe.setCraftId(craftId);
            recipe.setTasteId(tasteId);

            System.out.println(tat.toString());
            System.out.println(craft.toString());
            //保存  口味表和做法表
            crawUtil.craftService.addCraft(craft);
            crawUtil.tasteService.addTaste(tat);

            saveData(recipeDetail, recipe);

            //为防止爬去过快导致被拦截，这里休息5秒钟
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 保存数据
     * @param detailUrl
     * @param recipe
     */
    public void saveData(String detailUrl, Recipe recipe) {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(detailUrl);
        try {
            httpClient.executeMethod(getMethod);

            String text = getMethod.getResponseBodyAsString();

            Document document = Jsoup.parse(text);
            //烹饪技巧
            String cookingSkill = document.select("p[style=padding-left:100px;]").text();

            //难度
            Elements elements = document.select("span.processing");
            String difficulty = elements.attr("class");
//            System.out.println(difficulty);
            if (difficulty.contains("processing_nd")) {
                difficulty = difficulty.substring(difficulty.lastIndexOf("processing_nd") + 13);
            } else {
                //默认为0
                difficulty = "0";
            }

            //人数
            String persons = document.select("a[id=tongji_rsh]").text();
            persons = persons.replaceAll("[\\u4e00-\\u9fa5]", "");
            if (persons.length() > 0) {
                recipe.setPeopleNumber(Integer.parseInt(persons));
            }

            //准备时间
            String zbsj = document.select("a[id=tongji_zbsj]").text();

            if (zbsj.contains("分钟")) {
                zbsj = zbsj.substring(0, zbsj.indexOf("分"));
                zbsj = zbsj.replaceAll("[\\u4e00-\\u9fa5]", "").trim();
                if (zbsj != null && !"".equals(zbsj)) {
                    recipe.setPreparationTime(Integer.parseInt(zbsj));
                }
            } else if (zbsj.contains("小时")){
                zbsj = zbsj.substring(0, zbsj.indexOf("小"));
                zbsj = zbsj.replaceAll("[\\u4e00-\\u9fa5]", "").trim();
                if (zbsj != null && !"".equals(zbsj)) {
                    recipe.setPreparationTime(Integer.parseInt(zbsj) * 60);
                }
            } else {
                System.out.println(zbsj);
            }

            //烹饪时间  <30分钟
            String cookingTime = document.select("a[id=tongji_prsj]").text();

            if(cookingTime.contains("分钟")) {
                cookingTime = cookingTime.replaceAll("[^0-9]", "").trim();
                if (cookingTime != null && !"".equals(cookingTime)) {
                    recipe.setCookTime(Integer.parseInt(cookingTime));
                }
            } else if (cookingTime.contains("小时")) {
                cookingTime = cookingTime.replaceAll("[^0-9]", "").trim();
                if (cookingTime != null && !"".equals(cookingTime)) {
                    recipe.setCookTime(Integer.parseInt(cookingTime) * 60);
                }
            } else {
                System.out.println("烹饪时间==========" + cookingTime);
            }

            //简介
            String introduction = document.select("div.materials").select("p").text();
            //去除多余的双引号
            if (introduction.contains("“") && introduction.contains("”")) {
                introduction = introduction.substring(1, introduction.length() -1);
            }

            recipe.setCookingSkill(cookingSkill);
            recipe.setIntroduction(introduction);
            recipe.setDifficulty(Integer.parseInt(difficulty));

            //主料
            Elements zhuliaoDoc = document.select("div.yl.zl.clearfix").select("li");
            for (Element element : zhuliaoDoc) {
                Materials materials = new Materials();
                //主料图片
                String img = element.select("img").attr("src");
                //TODO:做图片保存
                try {

                    if (img != null && !"".equals(img)) {

                        byte[] imagedata = new FileIOStreamUtil().getImageFromNetByUrl(img);
                        //扩展名
                        String extName = img.substring(img.lastIndexOf(".") + 1);
                        String fileUrl = new FastDFSUtils().uploadFile(imagedata, extName);

                        img = fileUrl;

                        FileRecord fileRecord = new FileRecord();
                        fileRecord.setPictureUrl(fileUrl);
                        fileRecord.setUploadTime(new Date());
                        fileRecord.setPictureType(1);
                        fileRecord.setDelFlag(1);
                        fileRecord.setUploadUserId("1306523235645465465");

                        crawUtil.fileRecordService.saveFile(fileRecord);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                //主料材料
                String zhuliao = element.select("h4").select("a").text();
                //主料数量
                String number = element.select("h4").select("span").text();

                try {
                    materials.setMaterialsId(SnowflakeUtil.getSnowflakeId());
                    materials.setRecipeId(recipe.getRecipeId());
                    materials.setMaterialsImg(img);
                    materials.setMainMaterials(zhuliao);
                    materials.setMaterialsFlag(1);
                    materials.setMainNumber(number);

                    System.out.println(materials.toString());
                    //TODO:保存用料
                    crawUtil.materialsService.addMaterial(materials);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //辅料
            Elements fuliaoDoc = document.select("div.yl.fuliao.clearfix").select("li");
            for (Element element : fuliaoDoc) {
                Materials materials = new Materials();
                //辅料图片
                //String img = element.select("img").attr("src");
                //辅料材料
                String fuliao = element.select("h4").select("a").text();
                //辅料数量
                String number = element.select("h4").select("span").text();

                try {
                    materials.setMaterialsId(SnowflakeUtil.getSnowflakeId());
                    materials.setRecipeId(recipe.getRecipeId());
                    materials.setMainMaterials(fuliao);
                    materials.setMaterialsFlag(2);
                    materials.setMainNumber(number);

                    System.out.println(materials.toString());
                    //TODO:保存用料
                    crawUtil.materialsService.addMaterial(materials);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //食谱
            System.out.println(recipe.toString());
            crawUtil.recipeService.addRecipe(recipe);

            //步骤表
            Elements buzhouEles = document.select("div.content.clearfix");
            for (Element element : buzhouEles) {
                RecipeStep recipeStep = new RecipeStep();

                //步数     1.
                String bushu = element.select("em.step").text();
                bushu = bushu.replaceAll("\\.", "");

                //描述
                Elements miaoshuEles = element.select("p");
                String miaoshu = "";
                String img = "";
                for (Element miaoShuEle : miaoshuEles) {

                    Elements imgElement = miaoShuEle.select("img.conimg");
                    if (imgElement.size() != 0) {
                        //步骤图片
                        img = imgElement.attr("src");
                    } else {
                        miaoshu = miaoShuEle.text();
                    }
                }

                /*//步骤分为描述和图片   index0为描述，index1为图片
                if (miaoshuEles.size() != 2) {
                    //拦截步骤
                    break;
                }
                String miaoshu = miaoshuEles.get(0).text();
                //图片
                String img = miaoshuEles.get(1).select("img.conimg").attr("src");
*/
                try {

                    if (img != null && !"".equals(img)) {

                        String extName = img.substring(img.lastIndexOf(".") + 1);
                        byte[] imagedata = new FileIOStreamUtil().getImageFromNetByUrl(img);

                        String fileUrl = new FastDFSUtils().uploadFile(imagedata, extName);

                        img = fileUrl;

                        FileRecord fileRecord = new FileRecord();
                        fileRecord.setPictureUrl(fileUrl);
                        fileRecord.setUploadTime(new Date());
                        fileRecord.setPictureType(1);
                        fileRecord.setDelFlag(1);
                        // 爬取文件为管理员用户上传
                        fileRecord.setUploadUserId("1306523235645465465");

                        crawUtil.fileRecordService.saveFile(fileRecord);
                    }

                    recipeStep.setRecipeStepId(SnowflakeUtil.getSnowflakeId());
                    recipeStep.setRecipeId(recipe.getRecipeId());
                    recipeStep.setStepContent(miaoshu);
                    recipeStep.setStepNumber(Integer.parseInt(bushu));
                    //TODO:图片上传
                    recipeStep.setStepImg(img);

                    System.out.println(recipeStep.toString());
                    crawUtil.recipeStepService.addRecipeStep(recipeStep);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


            //功效表
            Elements effects = document.select("dl.yj_tags.clearfix").select("dt");
            for (Element element : effects) {
                Effect effect = new Effect();

                String effectName = element.text();
                effect.setEffectName(effectName);

                System.out.println(effect);
                Integer effectId = crawUtil.effectService.addEffect(effect);


                try {
                    //食谱功效表保存
                    RecipeEffect recipeEffect = new RecipeEffect();
                    recipeEffect.setRecipeEffectId(SnowflakeUtil.getSnowflakeId());
                    recipeEffect.setRecipeId(recipe.getRecipeId());
                    recipeEffect.setEffectId(effectId);

                    crawUtil.recipeEffectService.addRecipeEffect(recipeEffect);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //System.out.println("数小时".replaceAll("[\\u4e00-\\u9fa5]", ""));
        System.out.println("1.".replaceAll("\\.", ""));
        System.out.println("1.".replaceAll("1", ""));
    }

}
