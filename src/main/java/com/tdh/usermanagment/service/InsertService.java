package com.tdh.usermanagment.service;

import com.tdh.usermanagment.Dao.DepartmentTransform;
import com.tdh.usermanagment.Dao.GenderTransform;
import com.tdh.usermanagment.Dao.UserDao;
import com.tdh.usermanagment.cache.TDepartCache;
import com.tdh.usermanagment.cache.TGenderCache;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.utils.DateTransformUtil;
import com.tdh.usermanagment.utils.DepartGenderTransformUtil;
import com.tdh.usermanagment.utils.KeyQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : wangshanjie
 * 添加用户逻辑判断：
 *      主要完成：
 *          1. 从传入的tdhUser得到的部门代码和用户性别转换成相应的代码并存库（ 男 -> 09_00003-1 ， 立案庭 -> 32010001）
 *          2. 翻译排序号并存库 （是：1， 否：0）
 *          3. 判断tdhUser的传入的YHID是否已经存在数据库中，
 *              存在：直接返回
 *              不存在：-> 判断ID是不是"admin"（不可以包含！）
 *                          是：直接返回
 *                          否：进入下一轮判断逻辑
 *             用户id的长度必须小于等于14
 *          4. 判断排序号是否是整型数字：
 *                          是：进入下一轮逻辑判断
 *                          否：直接返回
 *          5.新增时将当前日期和当前时间分别存储到DJRQ和DJSJ字段中。
 *          6.前端字符串接收到的CSRQ需要从2023-12-15转化为20231215
 *          7.上面没有问题之后，最终入库
 */
public class InsertService {
    private final UserDao userDao = new UserDao();
    private final DateTransformUtil  dateTransformUtil = new DateTransformUtil();
    private final DepartGenderTransformUtil departGenderTransformUtil = new DepartGenderTransformUtil();
    /**
     * 添加用户的逻辑判断
     * @param tdhUser 用户对象
     * @return 成功返回ture 失败返回false
     */
    public MessageModel addUser(TdhUser tdhUser) {
        MessageModel messageModel = new MessageModel();
        try {
            // 1.转换部门和性别信息
            String yhbm = KeyQuery.ValueLookup(tdhUser.getYHBM(), TDepartCache.BMDM_BMMC_MAP);
            String yhxb = KeyQuery.ValueLookup(tdhUser.getYHXB(), TGenderCache.CODE_YHXB_MAP);
            tdhUser.setYHBM(yhbm);
            tdhUser.setYHXB(yhxb);

            // 2、转换是否激活的信息
            tdhUser.setSFJY("是".equals(tdhUser.getSFJY()) ? "1" : "0");

            //3. 判断tdhUser的传入的YHID是否已经存在数据库中，
            if (userDao.query_user(tdhUser.getYHID())!=null){
                messageModel.setCode(0);
                messageModel.setMsg("用户ID重复！请重新输入！");
                return messageModel;
            }
            //3、判断传入的用户的id是否包含admin字符串
            if(tdhUser.getYHID().contains("admin")){
                messageModel.setCode(0);
                messageModel.setMsg("用户ID不能包含admin！请重新输入！");
                return messageModel;
            }
            //3.用户id的长度必须小于等于14
            if(tdhUser.getYHID().length()>=15){
                messageModel.setCode(0);
                messageModel.setMsg("用户ID需要小于14！请重新输入！");
                return messageModel;
            }

            //4. 判断排序号是否是整型数字：
            if (tdhUser.getPXH() % 1 !=0){
                messageModel.setCode(0);
                messageModel.setMsg("排序号只能是整数！请重新输入！");
                return messageModel;
            }
            //5.新增时将当前日期和当前时间分别存储到DJRQ和DJSJ字段中。
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
                messageModel.setMsg("无法获取当前时间！系统错误！");
                return messageModel;
            }

            //6.前端字符串接收到的CSRQ需要从2023-12-15转化为20231215
            String user_csrq = tdhUser.getCSRQ();
            String  formattedcsrq = dateTransformUtil.dateTrans(user_csrq);
            tdhUser.setCSRQ(formattedcsrq);


            // 7、上面没有问题之后，最终入库
            boolean flag = userDao.add_user(tdhUser);
             if(!flag){
                 messageModel.setCode(0);
                 messageModel.setMsg("service层数据插入失败！");
                 return messageModel;
             }

        } catch (Exception e) {
            e.printStackTrace();
        }

        messageModel.setCode(1);
        messageModel.setMsg("添加成功！");
        return messageModel;
    }
}
