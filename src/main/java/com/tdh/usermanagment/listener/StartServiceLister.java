package com.tdh.usermanagment.listener;

import com.tdh.usermanagment.Dao.DepartmentTransform;
import com.tdh.usermanagment.Dao.GenderTransform;
import com.tdh.usermanagment.cache.TDepartCache;
import com.tdh.usermanagment.cache.TGenderCache;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author : wangshanjie
 * @date : 10:28 2023/12/18
 */

@WebListener
public class StartServiceLister implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
            System.out.println("Web应用程序正在启动...");
        //先把部门和性别信息写入到缓存中
        if(TGenderCache.CODE_YHXB_MAP.isEmpty()|| TDepartCache.BMDM_BMMC_MAP.isEmpty()){
            DepartmentTransform departmentT = new DepartmentTransform();
            GenderTransform genderT = new GenderTransform();
            departmentT.transfromDepartment();
            genderT.getGenderName();
        }
        System.out.println(TGenderCache.CODE_YHXB_MAP);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
