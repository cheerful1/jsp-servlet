package com.tdh.usermanagment.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
/**
 * @author : wangshanjie
 * 功能：随机生成出生日期。
 */
public class RandomBirthdateToString {

    /**
     * 功能：随机生成出生日期，日期范围在当前时间往前推100年间
     * @return LocalDate类对象，返回随机的日期：举例：2023-02-20
     */
    public static LocalDate generateRandomBirthdate() {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 设置随机范围，设置为过去 100 年内的日期
        LocalDate earliestDate = currentDate.minusYears(100);
        // 获取随机天数
        long randomDays = new Random().nextInt((int) currentDate.toEpochDay() - (int) earliestDate.toEpochDay());
        // 计算随机出生日期
        LocalDate randomBirthdate = earliestDate.plusDays(randomDays);
        return randomBirthdate;
    }

}