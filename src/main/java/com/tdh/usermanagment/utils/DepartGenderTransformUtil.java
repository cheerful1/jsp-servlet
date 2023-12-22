package com.tdh.usermanagment.utils;

import com.tdh.usermanagment.Dao.DepartmentTransform;
import com.tdh.usermanagment.Dao.GenderTransform;
import com.tdh.usermanagment.cache.TDepartCache;
import com.tdh.usermanagment.cache.TGenderCache;
import com.tdh.usermanagment.entity.TdhUser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : wangshanjie
 * 该类主要是转化部门代码和性别代码
 */
public class DepartGenderTransformUtil {
    /**
     * 该方法是一个转化工具类，主要功能：
     * 1. 后端返回给前端的用户部门和用户性别需要转化成中文（根据对应的code转化）
     *  即数据库中查出来的  09_00003-1 ——> 男  （性别转化）
     *                   32010001  ——> 立案庭 （部门转化）
     * 2. 用户是否禁用后端 1或 0  对应转化为
     *                  1——> 是
     *                  0——> 否
     *
     * @param tdhUserList
     * @return
     */
    public static List<TdhUser> DGtrans(List<TdhUser> tdhUserList){
        //将每一个user对象中的部门代码和性别转化一下
        for(TdhUser tUser : tdhUserList){
            String yhbm = KeyQuery.keyLookup(tUser.getYHBM(),TDepartCache.BMDM_BMMC_MAP);
            String yhxb = KeyQuery.keyLookup(tUser.getYHXB(),TGenderCache.CODE_YHXB_MAP);
            tUser.setYHBM(yhbm);
            tUser.setYHXB(yhxb);
            if(tUser.getSFJY().equals("1")){
                tUser.setSFJY("是");
            }else{
                tUser.setSFJY("否");
            }
        }
        return tdhUserList;
    }
}
