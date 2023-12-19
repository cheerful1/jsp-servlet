package com.tdh.usermanagment.test;

import com.tdh.usermanagment.entity.TdhUser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Main {
    public static void main(String[] args) {
        // 示例字符串
        String queryString = "user_id=1&user_name=1&user_pass=1&user_department=%E7%AB%8B%E6%A1%88%E5%BA%AD&user_gender=&user_birthday=&user_pxh=1";

        // 解码URL编码的字符串
        try {
            String decodedQueryString = URLDecoder.decode(queryString, "UTF-8");

            // 分割参数
            String[] params = decodedQueryString.split("&");

            // 创建TdhUser对象并设置属性
            TdhUser user = new TdhUser();
            for (String param : params) {
                String[] keyValue = param.split("=");
                String key = keyValue[0];
                String value = keyValue.length > 1 ? keyValue[1] : ""; // 处理没有值的情况
                setUserProperty(user, key, value);
            }

            // 打印TdhUser对象
            System.out.println(user);
            System.out.println(user.getYHID());
            System.out.println(user.getYHXM());
            System.out.println(user.getYHBM());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // 设置TdhUser对象的属性值
    private static void setUserProperty(TdhUser user, String key, String value) {
        switch (key) {
            case "user_id":
                user.setYHID(value);
                break;
            case "user_name":
                user.setYHXM(value);
                break;
            case "user_pass":
                user.setYHKL(value);
                break;
            case "user_department":
                user.setYHBM(value);
                break;
            case "user_gender":
                user.setYHXB(value);
                break;
            case "user_birthday":
                user.setCSRQ(value);
                break;
            case "user_pxh":
                try {
                    user.setPXH(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    // 处理无效的整数值
                    e.printStackTrace();
                }
                break;
            // 添加其他属性的处理
            // ...
        }
    }
}