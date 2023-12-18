package com.tdh.usermanagment.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : wangshanjie
 * @date : 10:25 2023/12/6
 */

/**
 * 定义部门缓存
 */
public class TDepartCache {
    /**
     *DepartmentMap 接收，查询数据库中的BMDM,BMMC字段，返回map，里面是<部门代码，部门名称>
     */
    public static final Map<String, Object> BMDM_BMMC_MAP = new HashMap<>();
}
