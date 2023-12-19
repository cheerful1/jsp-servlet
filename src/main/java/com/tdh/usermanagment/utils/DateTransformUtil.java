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
     * @param inputdate

     * @return
     */
    public static String dateTrans(String inputdate){
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            // 解析字符串为 LocalDate
            LocalDate date = LocalDate.parse(inputdate, inputFormatter);
            String outputdate = date.format(outputFormatter);
            return outputdate;
    }
    /**
     * 转化出生日期： CSRQ从20231215转化为2023-12-15
     *      inputdate：20231215
     *      outputdate：2023-12-15
     * @param inputdate

     * @return
     */
    public static String dateTrans1(String inputdate){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 解析字符串为 LocalDate
        LocalDate date = LocalDate.parse(inputdate, inputFormatter);
        String outputdate = date.format(outputFormatter);
        return outputdate;
    }
}
