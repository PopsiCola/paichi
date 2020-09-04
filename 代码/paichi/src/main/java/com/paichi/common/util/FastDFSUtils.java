package com.paichi.common.util;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * FastDFS文件上传、下载工具类
 * @Author liulebin
 * @Date 2020/9/4 8:43
 */
public class FastDFSUtils {

    private static Logger logger = LoggerFactory.getLogger(FastDFSUtils.class);

    private TrackerClient trackerClient = null;
    private TrackerServer trackerServer = null;
    private StorageServer storageServer = null;
    private StorageClient1 storageClient = null;

    public FastDFSUtils() throws Exception {
        String conf_filename = this.getClass().getClassLoader().getResource("fast_client.conf").getPath().replaceAll("%20"," ");
        conf_filename = URLDecoder.decode(conf_filename.substring(1), "utf-8");
        ClientGlobal.init(conf_filename);
        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getTrackerServer();
        storageServer = trackerClient.getStoreStorage(trackerServer);
        storageClient = new StorageClient1(trackerServer, storageServer);
    }

    /**
     * 上传文件方法
     * @param fileName  文件全路径
     * @param extName   文件扩展名，不包含（.）
     * @param metas     文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String result = storageClient.upload_file1(fileName, extName, metas);
        return result;
    }

    public String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null, null);
    }

    public String uploadFile(String fileName, String extName) throws Exception {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 上传文件方法
     * @param fileContent   文件的内容，字节数组
     * @param extName       文件扩展名
     * @param metas         文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {

        String result = storageClient.upload_file1(fileContent, extName, metas);
        return result;
    }

    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    public String uploadFile(byte[] fileContent, String extName) throws Exception {
        return uploadFile(fileContent, extName, null);
    }

    /**
     * 文件下载
     * @param filePath 文件地址
     * @param savePath 本地保存地址
     */
    public void download(String filePath,String savePath){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(savePath);
            byte[] bytes = storageClient.download_file1(filePath);
            fileOutputStream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件删除
     * @param filePath 文件的地址
     * @return
     */
    public Boolean deleteFile(String filePath){
        try {
            int i = storageClient.delete_file1(filePath);
            return i==0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取文件信息
     * @param filePath 文件的地址
     * @return
     */
    public String getFileInfo(String filePath){
        try {
            FileInfo fileInfo = storageClient.get_file_info1(filePath);
            //文件IP地址
            String sourceIpAddr = fileInfo.getSourceIpAddr();
            //文件大小
            long fileSize = fileInfo.getFileSize();
            //文件创建时间
            Date createTimestamp = fileInfo.getCreateTimestamp();
            //错误码
            long crc32 = fileInfo.getCrc32();
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
