package com.paichi.common.util;


import java.util.Random;

/**
 * 随机生成用户名
 * 命名规则：PaiChi_+随机字符
 * @Author liulebin
 * @Date 2020/9/27 21:55
 */
public class GenerateUserNameUtil {

    /**
     * 获取指定位数的随机字符
     * @param length    指定生成位数
     * @return
     */
    public static String generateString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0; i<length; i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 固定生成9位随机字符
     * @return
     */
    public static String generateString() {
        return generateString(9);
    }

    /**
     * 随机生成用户名
     * @return
     */
    public static String generateUserName() {
        return "PaiChi_" + generateString(9);
    }

    public static void main(String[] args) {
        String s = GenerateUserNameUtil.generateUserName();
        System.out.println(s);
    }

}
