package com.tdh.usermanagment.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author : wangshanjie
 * @date : 16:04 2023/12/19
 */
public class DateTransformUtil {
    /**
     * 转化出生日期： CSRQ从2023-12-15转化为20231215
     *      inputdate：2023-12-15
     *      outputdate：20231215
     * @return
     */
    public static String dateTrans(String inputDate) {
        if (inputDate == null || inputDate.isEmpty()) {
            return "";
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        try {
            // 解析字符串为 LocalDate
            LocalDate date = LocalDate.parse(inputDate, inputFormatter);
            // 格式化为输出字符串
            return date.format(outputFormatter);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 转化出生日期： CSRQ从20231215转化为2023-12-15
     *      inputdate：20231215
     *      outputdate：2023-12-15
     * @return
     */
    public static String dateTrans1(String inputDate) {
        if (inputDate == null || inputDate.isEmpty()) {
            // 如果输入为空，可以根据需要返回默认值或抛出异常
            return "";
        }

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // 解析字符串为 LocalDate
            LocalDate date = LocalDate.parse(inputDate, inputFormatter);
            // 格式化为输出字符串
            return date.format(outputFormatter);
        } catch (Exception e) {
            // 如果解析或格式化过程中发生异常，可以根据需要处理异常，例如记录日志或返回默认值
            e.printStackTrace();
            return "";
        }
    }
}
