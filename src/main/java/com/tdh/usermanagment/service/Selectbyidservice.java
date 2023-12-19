package com.tdh.usermanagment.service;

import com.tdh.usermanagment.Dao.DepartmentTransform;
import com.tdh.usermanagment.Dao.UserDao;
import com.tdh.usermanagment.cache.TDepartCache;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.utils.DateTransformUtil;
import com.tdh.usermanagment.utils.DepartGenderTransformUtil;
import com.tdh.usermanagment.utils.KeyQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : wangshanjie
 * @date : 14:30 2023/12/19
 */
public class Selectbyidservice {
    private final UserDao userDao = new UserDao();
    private final DateTransformUtil dateTransformUtil = new DateTransformUtil();

    /**
     * 主要功能：实现根据id来查询用户
     *          1、判断传来的yhid是否为空，为空就返回
     *          2、将查询到的用户的部门代码和用户性别转化成中文添加到tuser对象中
     *          3. 判断一下从数据库中查询到的user是否为空,不为空时最终返回给前端
     *                 其中：展示到前端需要转化一下部门代码和性别
     *                      数据库中没有给yhid赋值
     *
     *
     * @param yhid
     * @return 返回一个TdhUser对象
     */
    public MessageModel Selectbyid(String yhid) {
        MessageModel messageModel = new MessageModel();
        TdhUser tuser = new TdhUser();

        try {
            //2. 判断一下用户姓名长度不能太长
            if(yhid.isEmpty()||yhid == null){
                messageModel.setCode(0);
                messageModel.setMsg("查看失败！用户id为空！");
                return messageModel;
            }
            //3. 判断一下从数据库中查询到的user是否为空,不为空时最终返回给前端
            tuser = userDao.query_user(yhid);
            if(tuser !=null){
                messageModel.setCode(1);
                messageModel.setMsg("查询成功！");
                //4、展示到前端需要转化一下部门代码和性别
                DepartGenderTransformUtil departGenderTransformUtil = new DepartGenderTransformUtil();
                tuser = departGenderTransformUtil.DGtranssingle(tuser);
                //5.数据库中没有给yhid赋值
                tuser.setYHID(yhid);
                //6、出生日期需要从20231010转化成2023-10-10
                String csrq = tuser.getCSRQ();
                String tcsrq = dateTransformUtil.dateTrans1(csrq);
                tuser.setCSRQ(tcsrq);

                messageModel.setObject(tuser);
                return messageModel;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
