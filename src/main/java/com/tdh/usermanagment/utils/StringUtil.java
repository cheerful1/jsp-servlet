package com.tdh.usermanagment.utils;

/**
 * @author : wangshanjie
 */

/**
 * 字符串工具类
 */
public class StringUtil {
    /**
     * 判断字符串是否为空。
     * @param str 输入字符串
     * @return  如果为空，返回true 如果不为空返回false
     */
    public static boolean isEmpty(String str){
        if (str ==null || "".equals(str.trim())){
            return true;
        }
        return false;
    }
}


