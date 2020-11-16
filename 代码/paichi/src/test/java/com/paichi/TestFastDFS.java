package com.paichi;

import com.paichi.common.util.FastDFSUtils;
import com.paichi.modules.index.IndexController;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Author liulebin
 * @Date 2020/9/3 15:01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestFastDFS {

    public static String CONF_FILENAME  = TestFastDFS.class.getClassLoader().getResource("fast_client.conf").getFile();


    @Test
    public void testUpload() {
        // 1、加载配置文件，配置文件中的内容就是 tracker 服务的地址。其中%20支持空文件路径
        String conf_filename = this.getClass().getClassLoader().getResource("fast_client.conf").getPath().replaceAll("%20"," ");
        try {
            System.out.println(URLDecoder.decode(conf_filename.substring(1), "utf-8"));
            conf_filename = URLDecoder.decode(conf_filename.substring(1), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String path = "C:\\Users\\liule\\Pictures\\Camera Roll\\微信图片_20200903104218.jpg";
        String fileName = new File(path).getName();

        try {

            //fastDFS方式初始化
            ClientGlobal.init(conf_filename);


            //建立连接
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getTrackerServer();
            //StorageServer storageServer = tracker.getStoreStorage(trackerServer);
            StorageServer storageServer = null;
            StorageClient client = new StorageClient(trackerServer, storageServer);
            //获取组名
            String fileIds[] = client.upload_file(path, fileName,null);
            System.out.println(fileIds.length);
            System.out.println("组名：" + fileIds[0]);
            System.out.println("路径: " + fileIds[1]);

            System.out.println("/"+fileIds[0]+"/"+fileIds[1]);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }


    }

    /**
     * 上传文件
     */
    public static  String upload(String path,String extName) {
        try {
            String conf_filename = URLDecoder.decode(CONF_FILENAME.substring(1), "utf-8");
            ClientGlobal.init(conf_filename);
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getTrackerServer();
            StorageServer storageServer = null;
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
            String fileIds[] = storageClient.upload_file(path, extName,null);
            System.out.println(fileIds.length);
            System.out.println("组名：" + fileIds[0]);
            System.out.println("路径: " + fileIds[1]);
            return  "/"+fileIds[0]+"/"+fileIds[1];
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    @Test
    public void test() {
        System.out.println(new TestFastDFS().upload("C:\\Users\\liule\\Pictures\\Camera Roll\\微信图片_20200903104218.jpg", "微信图片_20200903104218.jpg"));
    }


    @Test
    public void testFastDFSUtils() {
        try {
            FastDFSUtils fastDFSUtils = new FastDFSUtils();
            String uploadFile = fastDFSUtils.uploadFile("C:\\Users\\liule\\Pictures\\Camera Roll\\微信图片_20200903104218.jpg", "jpg");
            System.out.println(uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
