package com.tdh.usermanagment.service;


import com.tdh.usermanagment.Dao.UserDao;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.utils.DateTransformUtil;
import com.tdh.usermanagment.utils.DepartGenderTransformUtil;
import com.tdh.usermanagment.utils.StringUtil;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : wangshanjie
 */

/**
 *用户服务层
 */
public class UserService {
    private static final String SALT = "tdh";
    private final UserDao userDao = new UserDao();
    private final DateTransformUtil dateTransformUtil = new DateTransformUtil();

    /**
     * 用户登录业务判断
     * @param uname 用户账户
     * @param upwd 用户密码
     * @return messageModel对象（状态码，提示信息，回显数据）
     *
     * 登录页面逻辑判断
     * 1.参数的非空判断：
     * 2.调用dao层的查询方法，通过用户ID查询对象
     * 3、判断用户对象是否为空
     * 4、数据库中的密码是加密的，因此加密校对，判断数据库中查询到的密码与前台传递过来的密码做比较
     * 5、登录成功，将用户信息设置到消息模型中
     */
    public MessageModel userLogin(String uname, String upwd) {
        MessageModel messageModel = new MessageModel();

        //回显数据
        TdhUser tuser = new TdhUser();
        tuser.setYHID(uname);
        tuser.setYHKL(upwd);
        messageModel.setObject(tuser);

        //1.参数的非空判断：
        if(StringUtil.isEmpty(uname) || StringUtil.isEmpty(upwd)){
            messageModel.setCode(0);
            messageModel.setMsg("用户姓名和密码不能为空");
            return messageModel;
        }
        //2.调用dao层的查询方法，通过用户ID查询对象
        UserDao userDao = new UserDao();
        TdhUser queryuser = userDao.query_user(uname);

        //3、判断用户对象是否为空
        if(queryuser == null){
            //将状态码，提示信息提示！
            messageModel.setCode(0);
            messageModel.setMsg("用户不存在！");
            return messageModel;
        }
        //4、数据库中的密码是加密的，因此加密校对
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + upwd).getBytes()).substring(0, 19);

        //4、判断数据库中查询到的密码与前台传递过来的密码做比较
        if(!encryptPassword.equals(queryuser.getYHKL())){
            //如果不相等，将状态码、提示信息、回显数据设置到消息模型对象中，返回消息对象模型
            messageModel.setCode(0);
            messageModel.setMsg("用户密码不正确！");
            return messageModel;
        }
        //5、登录成功，将用户信息设置到消息模型中
        messageModel.setCode(1);
        messageModel.setMsg("登录成功！");
        messageModel.setObject(queryuser);
        return messageModel;
    }


    /**
     * 添加用户的逻辑判断
     * @param tdhUser 用户对象
     * @return 成功返回ture 失败返回false
     */
    public MessageModel addUser(TdhUser tdhUser) {
        MessageModel messageModel = new MessageModel();
        try {
            //1、判断账户不能包含特殊字符
            String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            //使用Matcher类的find()方法来查找validPattern中的无效字符
            Matcher matcher = Pattern.compile(validPattern).matcher(tdhUser.getYHID());
            if (matcher.find()) {
                messageModel.setCode(0);
                messageModel.setMsg("用户ID存在非法的字符！请重新输入！");
                return messageModel;
            }
            //2. 判断tdhUser的传入的YHID是否已经存在数据库中，
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
            //4.判断登记时间和登记日期是否为空
            if (tdhUser.getDJRQ()==null||tdhUser.getDJSJ()==null){
                messageModel.setCode(0);
                messageModel.setMsg("无法获取当前时间！系统错误！");
                return messageModel;
            }
            //5、上面没有问题之后，最终入库
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


    /**
     * 更新用户信息的方法。
     *
     * @param tdhUser 包含更新信息的TdhUser对象
     * @return 包含更新结果的消息模型
     */
    public MessageModel updateUser(TdhUser tdhUser) {
        MessageModel messageModel = new MessageModel();
        try {
            // 没有问题之后，最终入库，入库是否成功判断
            int count =0;
            count = userDao.update_user(tdhUser);
            if(count<=0){
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


    /**
     * 主要功能：实现根据id来查询用户
     *          1、判断传来的yhid是否为空，为空就返回
     *          2、将查询到的用户的部门代码和用户性别转化成中文添加到tuser对象中
     *          3. 判断一下从数据库中查询到的user是否为空,不为空时最终返回给前端
     *                 其中：展示到前端需要转化一下部门代码和性别
     *                      数据库中没有给yhid赋值
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
                //5.数据库中没有给yhid赋值
                tuser.setYHID(yhid);
                //6、出生日期需要从20231010转化成2023-10-10
                if (tuser.getCSRQ()!=null && !tuser.getCSRQ().isEmpty()){
                    String csrq = tuser.getCSRQ();
                    String tcsrq = dateTransformUtil.dateTrans1(csrq);
                    tuser.setCSRQ(tcsrq);
                }
                //7.是否禁用翻译
                if (tuser.getSFJY().equals("1")) {
                    tuser.setSFJY("是");
                } else {
                    tuser.setSFJY("否");
                }
                messageModel.setObject(tuser);
                return messageModel;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 查询用户信息的方法。
     *
     * @param yhid_xm 用户ID或姓名（模糊查询）
     * @param yhbm    用户部门（部门代码或名称）
     * @return 包含查询结果的消息模型
     */
    public MessageModel queryUser(String yhid_xm, String yhbm) {
        MessageModel messageModel = new MessageModel();

        try {
            //2. 判断一下用户姓名长度不能太长
            if(yhid_xm.length()>=15){
                messageModel.setCode(0);
                messageModel.setMsg("查询用户ID需要小于14！请重新输入！");
                return messageModel;
            }
            //3. 判断一下从数据库中查询到的list是否为空
            // todo 合并(完成)
            List<TdhUser> list = new ArrayList<>();
            list = userDao.query_UserByID_BM(yhid_xm,yhbm);
            if(list==null){
                messageModel.setCode(0);
                messageModel.setMsg("查询失败，未查询到用户！");
                return messageModel;
            }
            //4、展示到前端需要转化一下部门代码和性别
            DepartGenderTransformUtil departGenderTransformUtil = new DepartGenderTransformUtil();
            list = departGenderTransformUtil.DGtrans(list);
            messageModel.setObject(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //4.上面没有问题之后，最终返回给前端
        messageModel.setCode(1);
        messageModel.setMsg("查询成功！");
        return messageModel;
    }


}










