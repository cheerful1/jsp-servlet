package com.tdh.usermanagment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.service.UserService;
import com.tdh.usermanagment.utils.RequestUtil;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * @author : wangshanjie
 */
@WebServlet("/add_users")
//编写一个类去继承HttpServlet类
public class InsertServlet extends HttpServlet {
    private UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request,response);
    }
    /**
     * 处理HTTP POST请求的方法，用于添加新用户。
     *
     * @param request HTTP请求对象，包含用户提交的数据
     * @param response HTTP响应对象，用于向客户端发送响应
     * @throws IOException 如果发生输入或输出异常
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //所有的请求都跳转到post里面来
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset = utf-8");

        try {
            // 创建TdhUser对象并设置属性
            TdhUser user = new TdhUser();
            //调用工具类将request请求中的字符串转化为实体类
            RequestUtil requestUtil = new RequestUtil();
            user = requestUtil.RequestToUser(request,user);
            // 调用 insertService 处理用户添加逻辑
            MessageModel addmessageModel = userService.addUser(user);
            //java对象转化成json，封装到request中返回到前端
            String requestString = objectMapper.writeValueAsString(addmessageModel);
            // 返回响应
            response.getWriter().write(requestString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

