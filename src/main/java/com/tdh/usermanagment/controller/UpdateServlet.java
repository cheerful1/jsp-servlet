package com.tdh.usermanagment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.service.UserService;
import com.tdh.usermanagment.utils.DateTransformUtil;
import org.springframework.util.DigestUtils;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * 更新用户信息的Servlet。
 * 映射路径：/update_user
 */
@WebServlet("/update_user")
//编写一个类去继承HttpServlet类
public class UpdateServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTransformUtil  dateTransformUtil = new DateTransformUtil();
    private static final String SALT = "tdh";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
    /**
     * 处理HTTP POST请求，更新用户信息。
     *
     * @param request  HTTP请求对象，包含客户端提交的数据
     * @param response HTTP响应对象，用于向客户端发送响应
     * @throws IOException      如果发生输入或输出异常
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //所有的请求都跳转到post里面来
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset = utf-8");
        try {
            // 创建TdhUser对象并设置属性
            TdhUser user = new TdhUser();
            //调用工具类将request请求中的字符串转化为实体类
            user = RequestToUser(request,user);
            // 调用 insertService 处理用户添加逻辑
            MessageModel addmessageModel = userService.updateUser(user);
            //java对象转化成json，封装到request中返回到前端
            String requestString = objectMapper.writeValueAsString(addmessageModel);
            response.getWriter().write(requestString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * request中字符串转tuser对象
     * @param request
     * @param tuser
     * @return
     */
    private static TdhUser RequestToUser(HttpServletRequest request, TdhUser tuser) {

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
            tuser.setCSRQ(user_birthday);
            tuser.setYHXB(user_gender);
            tuser.setSFJY("是".equals(user_disable) ? "1" : "0");

            //1. 如果接收到的信息为空
            if(tuser.getYHXB()==null || tuser.getYHXB().isEmpty()){
                tuser.setYHXB("09_00003-1");
            }
            if (user_pxhStr == null || user_pxhStr.isEmpty()){
                tuser.setPXH(0);
            }else {
                int user_pxh = Integer.parseInt(user_pxhStr);
                tuser.setPXH(user_pxh);
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
            tuser.setDJRQ(djrq);
            tuser.setDJSJ(djsj);

            //5. 如果接收到的字符串为空：那么不转化
            //   如果不为空需要转化下，前端字符串接收到的CSRQ需要从2023-12-15转化为20231215
            if (tuser.getCSRQ()!=null && !tuser.getCSRQ().isEmpty()) {
                String user_csrq = tuser.getCSRQ();
                String  formattedcsrq = dateTransformUtil.dateTrans(user_csrq);
                tuser.setCSRQ(formattedcsrq);
            }

            //6。判断用户是否禁用
            if (user_disable==null||user_disable.isEmpty()){
                tuser.setSFJY("0");
            }
            //7、用户密码加密进数据库
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + tuser.getYHKL()).getBytes()).substring(0, 19);
            tuser.setYHKL(encryptPassword);

        } catch (NumberFormatException e) {
            // 抛出异常
            throw new IllegalArgumentException("user_pxh 参数不是有效的整数：" + user_pxhStr, e);
        }
        return tuser;
    }

}
