package com.tdh.usermanagment.service;

import com.tdh.usermanagment.Dao.DepartmentTransform;
import com.tdh.usermanagment.Dao.GenderTransform;
import com.tdh.usermanagment.Dao.UserDao;
import com.tdh.usermanagment.cache.TDepartCache;
import com.tdh.usermanagment.cache.TGenderCache;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.utils.KeyQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : wangshanjie
 * 更新用户逻辑判断：
 *      主要完成：
 *          1. 从传入的tdhUser得到的部门代码和用户性别转换成相应的代码并存库（ 男 -> 09_00003-1 ， 立案庭 -> 32010001）
 *          2. 翻译排序号并存库 （是：1， 否：0）
 *          3. 判断排序号是否是整型数字：
 *                          是：进入下一轮逻辑判断
 *                          否：直接返回
 *          4.新增时将当前日期和当前时间分别存储到DJRQ和DJSJ字段中。
 *          5.上面没有问题之后，最终更新入库
 */
public class UpdateService {
    private final UserDao userDao = new UserDao();
    public MessageModel updateUser(TdhUser tdhUser) {
        MessageModel messageModel = new MessageModel();
        try {
            // 1.先把部门和性别信息写入到缓存中
            if (TGenderCache.CODE_YHXB_MAP.isEmpty() || TDepartCache.BMDM_BMMC_MAP.isEmpty()) {
                DepartmentTransform departmentT = new DepartmentTransform();
                GenderTransform genderT = new GenderTransform();
                departmentT.transfromDepartment();
                genderT.getGenderName();
            }
            // 1.转换部门和性别信息
            String yhbm = KeyQuery.ValueLookup(tdhUser.getYHBM(), TDepartCache.BMDM_BMMC_MAP);
            String yhxb = KeyQuery.ValueLookup(tdhUser.getYHXB(), TGenderCache.CODE_YHXB_MAP);
            tdhUser.setYHBM(yhbm);
            tdhUser.setYHXB(yhxb);

            // 2、转换是否激活的信息
            tdhUser.setSFJY("是".equals(tdhUser.getSFJY()) ? "1" : "0");


            // 3. 判断排序号是否是整型数字：
            if (tdhUser.getPXH() % 1 !=0){
                messageModel.setCode(0);
                messageModel.setMsg("更新失败！排序号只能是整数！请重新输入！");
                return messageModel;
            }
            // 4.新增时将当前日期和当前时间分别存储到DJRQ和DJSJ字段中。
            LocalDate currentDate = LocalDate.now(); //yyyy-MM-dd
            LocalDateTime currentDateTime = LocalDateTime.now();//"yyyy-MM-dd HH:mm:ss"
            // 定义日期格式
            DateTimeFormatter current_djrq = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter current_djsj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            // 将 LocalDate 对象转换为字符串
            String djrq = currentDate.format(current_djrq);
            String djsj = currentDateTime.format(current_djsj);
            tdhUser.setDJRQ(djrq);
            tdhUser.setDJSJ(djsj);

            if (tdhUser.getDJRQ()==null||tdhUser.getDJSJ()==null){
                messageModel.setCode(0);
                messageModel.setMsg("更新失败！无法获取当前时间！系统错误！");
                return messageModel;
            }


            // 5、上面没有问题之后，最终入库，入库是否成功判断
            if(userDao.update_user(tdhUser)<=0){
                messageModel.setCode(0);
                messageModel.setMsg("service层更新失败！");
                return messageModel;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        messageModel.setCode(1);
        messageModel.setMsg("更新成功！");
        return messageModel;
    }
}