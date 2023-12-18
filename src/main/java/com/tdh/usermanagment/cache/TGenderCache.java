package com.tdh.usermanagment.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : wangshanjie
 * @date : 10:55 2023/12/6
 */

/**
 * 定义性别缓存
 */
public class TGenderCache {
    /**
     * GenderMap,查询数据库中的CODE,MC，两者拼接返回map，里面缓存的是<性别代码，性别>
     */
    public static final Map<String, Object> CODE_YHXB_MAP = new HashMap<>();
}
