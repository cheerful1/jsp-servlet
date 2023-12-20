package com.tdh.usermanagment.service;


import com.tdh.usermanagment.Dao.UserDao;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.utils.StringUtil;
import org.springframework.util.DigestUtils;

/**
 * @author : wangshanjie
 */

/**
 *用户服务层
 */
public class UserService {
    private static final String SALT = "tdh";

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

}










