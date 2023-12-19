package com.tdh.usermanagment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.service.InsertService;
import com.tdh.usermanagment.utils.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


/**
 * @author : wangshanjie
 * @date :
 */
@WebServlet("/add_users")
//编写一个类去继承HttpServlet类
public class InsertServlet extends HttpServlet {
    private final InsertService insertService = new InsertService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
            MessageModel addmessageModel = insertService.addUser(user);
            //java对象转化成json，封装到request中返回到前端
            String requestString = objectMapper.writeValueAsString(addmessageModel);
            // 返回响应
            if (addmessageModel.getCode()==1) {
                // 将 JSON 字符串写入响应流
                response.getWriter().write(requestString);
            } else {
                //失败也将消息返回给页面
                response.getWriter().write(requestString);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //todo 这里可以优化？？
            MessageModel messageModel = new MessageModel();
            messageModel.setCode(0);
            messageModel.setMsg("保存失败！排序号必须是整数！");
            String errorResponseString = objectMapper.writeValueAsString(messageModel);
            response.getWriter().write(errorResponseString);
        }
    }

}

