package com.tdh.usermanagment.service;

import com.tdh.usermanagment.Dao.DepartmentTransform;
import com.tdh.usermanagment.Dao.GenderTransform;
import com.tdh.usermanagment.Dao.UserDao;
import com.tdh.usermanagment.cache.TDepartCache;
import com.tdh.usermanagment.cache.TGenderCache;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.utils.DepartGenderTransformUtil;
import com.tdh.usermanagment.utils.KeyQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : wangshanjie
 * 查询用户逻辑判断：
 *      主要完成：
 *          1. 从前端传输的两个参数，yhid_xm和yhbm
 *          2. 判断一下用户姓名长度不能太长
 *          3. 判断一下从数据库中查询到的list是否为空
 *          4.上面没有问题之后，最终返回给前端
 */
public class QueryService {
    private final UserDao userDao = new UserDao();

    public MessageModel queryUser(String yhid_xm, String yhbm) {
        MessageModel messageModel = new MessageModel();
        //1.将前端得到的用户部门转化为用户代码  立案庭——>32010001 这样再去查
        if (TDepartCache.BMDM_BMMC_MAP.isEmpty()) {
            DepartmentTransform departmentT = new DepartmentTransform();
            departmentT.transfromDepartment();
        }
        // 1.转换部门信息
        String tyhbm = KeyQuery.ValueLookup(yhbm, TDepartCache.BMDM_BMMC_MAP);
        try {
            //2. 判断一下用户姓名长度不能太长
            if(yhid_xm.length()>=15){
                messageModel.setCode(0);
                messageModel.setMsg("查询用户ID需要小于14！请重新输入！");
                return messageModel;
            }
            //3. 判断一下从数据库中查询到的list是否为空
            if(userDao.query_UserByID_BM(yhid_xm,tyhbm)==null){
                messageModel.setCode(0);
                messageModel.setMsg("查询失败，未查询到用户！");
                return messageModel;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //4.上面没有问题之后，最终返回给前端
        messageModel.setCode(1);
        messageModel.setMsg("查询成功！");
        List<TdhUser> list = new ArrayList<>();
        list = userDao.query_UserByID_BM(yhid_xm,tyhbm);
        //4、展示到前端需要转化一下部门代码和性别
        DepartGenderTransformUtil departGenderTransformUtil = new DepartGenderTransformUtil();
        list = departGenderTransformUtil.DGtrans(list);
        messageModel.setObject(list);
        return messageModel;
    }

}
