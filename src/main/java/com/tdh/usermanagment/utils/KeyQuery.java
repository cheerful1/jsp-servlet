package com.tdh.usermanagment.utils;

import java.util.Map;

/**
 * @author : wangshanjie
 * @date : 12:45 2023/12/5
 * 作用：给定一个map，根据提供的key值，返回value值
 */
public class KeyQuery {
    /**
     * 部门代码转换和性别代码转换都需要用到这个函数
     * @param departmentcode String类型输入，可以是部门代码，或者是性别的代码
     * @param map 输入是一个map，可接收查出数据库后返回的map
     * @return 如果当前map含有该key(departmentcode),返回该key对应的值。如果没有就返回null。
     */
    public static String keyLookup(String departmentcode, Map<String,Object> map){
        if(map.containsKey(departmentcode)){
            return (String) map.get(departmentcode);
        }else{
            return null;
        }
    }

    public static String ValueLookup(String targetValue, Map<String,Object> map){
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            // 判断值是否为字符串，并且与目标值相等
            if (value instanceof String && ((String) value).equals(targetValue)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
