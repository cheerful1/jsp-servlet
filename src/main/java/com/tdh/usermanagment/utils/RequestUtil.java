package com.tdh.usermanagment.utils;


import com.tdh.usermanagment.entity.TdhUser;
import javax.servlet.http.HttpServletRequest;


/**
 * @author : wangshanjie
 * @date : 11:06 2023/12/19
 */
public class RequestUtil {
    /**
     * request中字符串转tuser对象
     * @param request
     * @param tuser
     * @return
     */
    public static TdhUser RequestToUser(HttpServletRequest request, TdhUser tuser) {
            String user_id = request.getParameter("user_id");
            String user_name = request.getParameter("user_name");
            String user_pass = request.getParameter("user_pass");
            String user_department = request.getParameter("user_department");
            String user_gender = request.getParameter("user_gender");
            String user_birthday = request.getParameter("user_birthday");
            String user_disable = request.getParameter("user_disable");
            String user_pxhStr = request.getParameter("user_pxh");
            try {
                // 将获取的参数设置到 TdhUser 对象中
                tuser.setYHID(user_id);
                tuser.setYHXM(user_name);
                tuser.setYHKL(user_pass);
                tuser.setYHBM(user_department);
                tuser.setYHXB(user_gender);
                tuser.setCSRQ(user_birthday);
                tuser.setSFJY(user_disable);

                if (user_pxhStr == null || user_pxhStr.isEmpty()){
                    tuser.setPXH(0);
                }else {
                    int user_pxh = Integer.parseInt(user_pxhStr);
                    tuser.setPXH(user_pxh);
                }

            } catch (NumberFormatException e) {
                // 抛出异常
                throw new IllegalArgumentException("user_pxh 参数不是有效的整数：" + user_pxhStr, e);
            }
            return tuser;
        }

}
