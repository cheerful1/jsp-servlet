package com.tdh.usermanagment.utils;

import java.util.Random;

/**
 * 随机字符串生成！
 */

public class RandomStringGenerator {

    /**
     * 功能：随机生成长度为40的字符串，其中字符串首字母大写。
     * @return 返回类型String。返回内容：长度为40的随机字符串，举例：Bcbsyizsrwlfsxruyjqsirvuexb
     */
    public static String generateRandomString() {
        Random random = new Random();
        int length = random.nextInt(40) + 1; // 生成1到40之间的随机长度

        StringBuilder randomString = new StringBuilder();
        randomString.append((char) (random.nextInt(26) + 'A')); // 第一位为大写字母

        for (int i = 1; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // 生成小写字母
            randomString.append(randomChar);
        }
        return randomString.toString();
    }
}