package com.paichi;

import com.paichi.common.util.MinioUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author liulebin
 * @Date 2020/11/4 14:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestMinio {

    @Autowired
    private MinioUtils minioUtils;

    /*@Test
    public void existBucket() {
        boolean paiChi = minioUtils.bucketExists("dafaf");
        System.out.println(paiChi);
    }

    @Test
    public void listBuckets() {
        List<Bucket> buckets = minioUtils.listBuckets();
        buckets.forEach(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void listBucketsName() {
        List<String> buckets = minioUtils.listBucketNames();
        buckets.forEach(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void listObjectNames() {
        List<String> buckets = minioUtils.listObjectNames("paichi");
        buckets.forEach(str -> {
            System.out.println(str);
        });
    }

    @Test
    public void putObject() {
        boolean b = minioUtils.putObject("paichi", "qqqqqqq.png",
                "C:\\Users\\liule\\Pictures\\Camera Roll\\1a3fcca2282682e73841648a0dfde1e2_1.jpg");
        System.out.println(b);
    }

    @Test
    public void putObject1() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File("C:\\Users\\liule\\Pictures\\Camera Roll\\1a3fcca2282682e73841648a0dfde1e2_1.jpg"));
        boolean paichi = minioUtils.putObject("paichi", "1a3fcca2282682e73841648a0dfde1e2_1.jpg", inputStream);
        System.out.println(paichi);
    }

    @Test
    public void getObject() {
        InputStream paichi = minioUtils.getObject("paichi", "qqqqqqq.png");
        System.out.println(paichi);
    }

    @Test
    public void getObject1() {
        boolean paichi = minioUtils.getObject("paichi", "qqqqqqq.png", "111.png");
        System.out.println(paichi);
    }
    @Test
    public void removeObject() {
        boolean paichi = minioUtils.removeObject("paichi", "qqqqqqq.png");
        System.out.println(paichi);
    }*/

    @Test
    public void getObject1() throws FileNotFoundException {
        //MultipartFile multipartFile =
        //minioUtils.multipartFileUpload("paichi", , "1111111111.png");
        //InputStream inputStream = new FileInputStream(new File("C:\\Users\\liule\\Pictures\\Camera Roll\\1a3fcca2282682e73841648a0dfde1e2_1.jpg"));
        //boolean paichi = minioUtils.putObject("paichi", "1111.jpg", inputStream);
        //System.out.println(paichi);
    }

    @Test
    public void getObject2() throws FileNotFoundException {
        //MultipartFile multipartFile =
        //minioUtils.multipartFileUpload("paichi", , "1111111111.png");
        InputStream inputStream = new FileInputStream(new File("C:\\Users\\liule\\Pictures\\Camera Roll\\1a3fcca2282682e73841648a0dfde1e2_1.jpg"));
        boolean paichi = minioUtils.putObject("paichi", "1111.jpg", inputStream, "image/png");
        System.out.println(paichi);
    }
}
