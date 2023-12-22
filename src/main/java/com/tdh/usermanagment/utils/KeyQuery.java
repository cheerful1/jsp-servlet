package com.tdh.usermanagment.utils;

import java.util.Map;

/**
 * @author : wangshanjie
 * 作用：给定一个map，根据提供的key值，返回value值
 */
public class KeyQuery {
    /**
     * 部门代码转换和性别代码转换都需要用到这个函数
     * @param departmentCode String类型输入，可以是部门代码，或者是性别的代码
     * @param map 输入是一个map，可接收查出数据库后返回的map
     * @return 如果当前map含有该key(departmentcode),返回该key对应的值。如果没有就返回null。
     */
    public static String keyLookup(String departmentCode, Map<String, Object> map) {
        // 判空
        if (departmentCode == null || departmentCode.isEmpty() || map == null) {
            return null;
        }

        if (map.containsKey(departmentCode)) {
            return (String) map.get(departmentCode);
        } else {
            return null;
        }
    }
}
