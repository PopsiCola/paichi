package com.paichi.common.util;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式化工具类
 * @Author liulebin
 * @Date 2020/11/4 15:46
 */
public class DateUtils {

    private static String getToday() {
        return new SimpleDateFormat("yyyy-dd").format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(DateUtils.getToday());
    }
}
