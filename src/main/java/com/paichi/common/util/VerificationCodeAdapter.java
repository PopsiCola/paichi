package com.paichi.common.util;

import com.paichi.modules.record.entity.FileRecord;
import com.paichi.modules.record.service.IFileRecordService;
import com.paichi.modules.verifyImage.entity.VerificationCodePlace;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Component
public class VerificationCodeAdapter {

    @Autowired
    private IFileRecordService fileRecordService;

    /**
     * 源文件宽度
     */
    private static int ORI_WIDTH = 300;
    /**
     * 源文件高度
     */
    private static int ORI_HEIGHT = 150;
    /**
     * 模板图宽度
     */
    private static int CUT_WIDTH = 50;
    /**
     * 模板图高度
     */
    private static int CUT_HEIGHT = 50;
    /**
     * 抠图凸起圆心
     */
    private static int circleR = 5;
    /**
     * 抠图内部矩形填充大小
     */
    private static int RECTANGLE_PADDING = 8;
    /**
     * 抠图的边框宽度
     */
    private static int SLIDER_IMG_OUT_PADDING = 1;


    // 生成拼图样式
    private static int[][] getBlockData(){
        int[][] data = new int[CUT_WIDTH][CUT_HEIGHT];
        Random random = new Random();
        //(x-a)²+(y-b)²=r²
        //x中心位置左右5像素随机
        double x1 = RECTANGLE_PADDING + (CUT_WIDTH - 2 * RECTANGLE_PADDING) / 2.0 - 5 + random.nextInt(10);
        //y 矩形上边界半径-1像素移动
        double y1_top = RECTANGLE_PADDING - random.nextInt(3);
        double y1_bottom = CUT_HEIGHT - RECTANGLE_PADDING + random.nextInt(3);
        double y1 = random.nextInt(2) == 1 ? y1_top : y1_bottom;


        double x2_right = CUT_WIDTH - RECTANGLE_PADDING - circleR + random.nextInt(2 * circleR - 4);
        double x2_left = RECTANGLE_PADDING + circleR - 2 - random.nextInt(2 * circleR - 4);
        double x2 = random.nextInt(2) == 1 ? x2_right : x2_left;
        double y2 = RECTANGLE_PADDING + (CUT_HEIGHT - 2 * RECTANGLE_PADDING) / 2.0 - 4 + random.nextInt(10);

        double po = Math.pow(circleR, 2);
        for (int i = 0; i < CUT_WIDTH; i++) {
            for (int j = 0; j < CUT_HEIGHT; j++) {
                //矩形区域
                boolean fill;
                if ((i >= RECTANGLE_PADDING && i < CUT_WIDTH - RECTANGLE_PADDING)
                        && (j >= RECTANGLE_PADDING && j < CUT_HEIGHT - RECTANGLE_PADDING)) {
                    data[i][j] = 1;
                    fill = true;
                } else {
                    data[i][j] = 0;
                    fill = false;
                }
                //凸出区域
                double d3 = Math.pow(i - x1, 2) + Math.pow(j - y1, 2);
                if (d3 < po) {
                    data[i][j] = 1;
                } else {
                    if (!fill) {
                        data[i][j] = 0;
                    }
                }
                //凹进区域
                double d4 = Math.pow(i - x2, 2) + Math.pow(j - y2, 2);
                if (d4 < po) {
                    data[i][j] = 0;
                }
            }
        }
        //边界阴影
        for (int i = 0; i < CUT_WIDTH; i++) {
            for (int j = 0; j < CUT_HEIGHT; j++) {
                //四个正方形边角处理
                for (int k = 1; k <= SLIDER_IMG_OUT_PADDING; k++) {
                    //左上、右上
                    if (i >= RECTANGLE_PADDING - k && i < RECTANGLE_PADDING
                            && ((j >= RECTANGLE_PADDING - k && j < RECTANGLE_PADDING)
                            || (j >= CUT_HEIGHT - RECTANGLE_PADDING - k && j < CUT_HEIGHT - RECTANGLE_PADDING +1))) {
                        data[i][j] = 2;
                    }

                    //左下、右下
                    if (i >= CUT_WIDTH - RECTANGLE_PADDING + k - 1 && i < CUT_WIDTH - RECTANGLE_PADDING + 1) {
                        for (int n = 1; n <= SLIDER_IMG_OUT_PADDING; n++) {
                            if (((j >= RECTANGLE_PADDING - n && j < RECTANGLE_PADDING)
                                    || (j >= CUT_HEIGHT - RECTANGLE_PADDING - n && j <= CUT_HEIGHT - RECTANGLE_PADDING ))) {
                                data[i][j] = 2;
                            }
                        }
                    }
                }

                if (data[i][j] == 1 && j - SLIDER_IMG_OUT_PADDING > 0 && data[i][j - SLIDER_IMG_OUT_PADDING] == 0) {
                    data[i][j - SLIDER_IMG_OUT_PADDING] = 2;
                }
                if (data[i][j] == 1 && j + SLIDER_IMG_OUT_PADDING > 0 && j + SLIDER_IMG_OUT_PADDING < CUT_HEIGHT && data[i][j + SLIDER_IMG_OUT_PADDING] == 0) {
                    data[i][j + SLIDER_IMG_OUT_PADDING] = 2;
                }
                if (data[i][j] == 1 && i - SLIDER_IMG_OUT_PADDING > 0 && data[i - SLIDER_IMG_OUT_PADDING][j] == 0) {
                    data[i - SLIDER_IMG_OUT_PADDING][j] = 2;
                }
                if (data[i][j] == 1 && i + SLIDER_IMG_OUT_PADDING > 0 && i + SLIDER_IMG_OUT_PADDING < CUT_WIDTH && data[i + SLIDER_IMG_OUT_PADDING][j] == 0) {
                    data[i + SLIDER_IMG_OUT_PADDING][j] = 2;
                }
            }
        }
        return data;
    }

    // 抠出拼图
    private static void cutImgByTemplate(BufferedImage oriImage, BufferedImage targetImage, int[][] blockImage, int x, int y) {
        for (int i = 0; i < CUT_WIDTH; i++) {
            for (int j = 0; j < CUT_HEIGHT; j++) {
                int _x = x + i;
                int _y = y + j;
                int rgbFlg = blockImage[i][j];
                int rgb_ori = oriImage.getRGB(_x, _y);
                // 原图中对应位置变色处理
                if (rgbFlg == 1) {
                    //抠图上复制对应颜色值
                    targetImage.setRGB(i,j, rgb_ori);
                    //原图对应位置颜色变化
                    oriImage.setRGB(_x, _y, Color.LIGHT_GRAY.getRGB());
                } else if (rgbFlg == 2) {
                    targetImage.setRGB(i, j, Color.WHITE.getRGB());
                    oriImage.setRGB(_x, _y, Color.GRAY.getRGB());
                }else if(rgbFlg == 0){
                    //int alpha = 0;
                    targetImage.setRGB(i, j, rgb_ori & 0x00ffffff);
                }
            }

        }
    }

    // 获取图片
    private static BufferedImage getBufferedImage(String path) throws IOException{
        File file = new File(path);
        if(file.isFile()){
            return ImageIO.read(file);
        }
        return null;
    }

    /**
     * 存放图片到FastDFS服务器
     * @param image 图片流
     * @throws Exception
     */
    private String writeImg(BufferedImage image) throws Exception {
        byte[] imagedata = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image,"png",bos);
        imagedata = bos.toByteArray();
        // TODO 临时文件保存
        // String uploadFile = new FastDFSUtils().uploadFile(imagedata, "png");
        String uploadFile = "";

        //保存文件上传记录到数据库
        FileRecord fileRecord = new FileRecord();
        fileRecord.setPictureUrl(uploadFile);
        fileRecord.setPictureType(2);
        fileRecord.setUploadTime(new Date());
        fileRecord.setDelFlag(1);

        fileRecordService.saveFile(fileRecord);

        return uploadFile;
    }

    /**
     * 对图片进行裁剪切割，保存切割后的两个拼图图片
     * @param imgName   图片名
     * @param path      图片路径：static/image下的图片
     * @param data      随机产生的拼图位置
     * @return          返回拼图图片的地址、以及拼图的x、y位置
     * @throws Exception
     */
    private VerificationCodePlace cutAndSave(String imgName, String path, int[][] data) throws Exception {
        VerificationCodePlace vcPlace =
                new VerificationCodePlace("sample_after.png", "sample_after_mark.png", 112, 50);

        // 进行图片处理
        BufferedImage originImage = getBufferedImage(path);
        if(originImage!=null) {
            int locationX = CUT_WIDTH + new Random().nextInt(originImage.getWidth() - CUT_WIDTH * 3);
            int locationY = CUT_HEIGHT + new Random().nextInt(originImage.getHeight() - CUT_HEIGHT) / 2;
            BufferedImage markImage = new BufferedImage(CUT_WIDTH, CUT_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
            cutImgByTemplate(originImage, markImage, data, locationX, locationY);

            String name = imgName.substring(0, imgName.indexOf('.'));

            // 考虑图片覆盖,简单设置四位随机数
            int r = (int)Math.round(Math.random() * 8999) + 1000;

            //文件上传到FastDFS服务器
            String afterName = this.writeImg(originImage);
            String markName = this.writeImg(markImage);

            vcPlace = new VerificationCodePlace(afterName, markName, locationX, locationY);
        }

        return vcPlace;
    }

    /**
     * 获取文件夹下所有文件名
     * @param dicPath 文件夹目录
     * @return
     */
    private static ArrayList<String> getFileNamesFromDic(String dicPath){
        File dic = new File(dicPath);
        ArrayList<String> imageFileNames = new ArrayList<String>();
        File[] dicFileList = dic.listFiles();
        for(File f: dicFileList){
            imageFileNames.add(f.getName());
        }
        return imageFileNames;
    }

    // 总流程，随机获取图片并处理，将拼图和对应图片存放至after_img
    // 出错则返回sample
    // headPath为存放生成图片的文件夹地址
    public VerificationCodePlace getRandomVerificationCodePlace() {
        VerificationCodePlace vcPlace = new VerificationCodePlace("sample_after.png", "sample_mark_after.png", 112, 50);

        // 从文件夹中读取所有待选择文件
        String directoryPath = "src/main/resources/static/image/puzzle";
        ArrayList<String> imageFileNames = getFileNamesFromDic(directoryPath);

        // 随机获取
        int r = (int)Math.round(Math.random() * (imageFileNames.size() - 1));
        String imgName = imageFileNames.get(r);
        String path = "src/main/resources/static/image/puzzle/" + imgName;
        int[][] data = VerificationCodeAdapter.getBlockData();

        // 进行图片处理
        try {
            vcPlace = this.cutAndSave(imgName, path, data);
        } catch (Exception e) {
            e.printStackTrace();
            return vcPlace;
        }

        return vcPlace;
    }

    /**
     * 删除保存的图片文件
     * @TODO 由于空间限制，以及验证码的作用，将会设置定时任务来定时删除，节省内存空间
     * @param headPath
     * @return
     */
    public static String deleteAfterImage(String headPath){
        boolean successDelete = true;
        int sum = 0;
        float fileSize = 0;
        String directoryPath = headPath;
        File dic = new File(directoryPath);
        File[] dicFileList = dic.listFiles();
        if(dicFileList != null) {
            for (File f : dicFileList) {
                if (!f.getName().equals("sample_after.png") && !f.getName().equals("sample_after_mark.png")) {
                    long fLength = f.length();
                    successDelete = f.delete();
                    if(!successDelete)
                        break;
                    sum ++;
                    fileSize += fLength;

                }
            }
        }
        float fileSizeInMB = fileSize / 1024 / 1024;
        if(!successDelete){
            String tip = "拼图文件删除中出现错误，请到" + directoryPath + "中进行查看";
            System.out.println(tip);
            return tip;
        }else{
            String tip = "拼图文件删除成功，删除文件数量为" + sum + ",文件总大小为" + fileSizeInMB + "MB";
            System.out.println(tip);
            return tip;
        }
    }

}
