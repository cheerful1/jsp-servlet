package com.tdh.usermanagment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.service.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 根据用户ID查询用户信息的Servlet。
 * 映射路径：/selectbyid
 */
@WebServlet("/selectbyid")
//编写一个类去继承HttpServlet类
public class SelectByIdServlet extends HttpServlet {
    private UserService userService = new UserService();

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request,response);
    }
    /**
     * 处理HTTP POST请求，根据用户ID查询用户信息。
     *
     * @param request  HTTP请求对象，包含客户端提交的数据
     * @param response HTTP响应对象，用于向客户端发送响应
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try{
            //所有的请求都跳转到post里面来
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset = utf-8");
            //1.获取前端得到的yhid
            String yhid = request.getParameter("yhid");

            //2.调用service层的方法返回消息对象
            MessageModel messageModel = userService.Selectbyid(yhid);

            //java对象转化成json，封装到request中返回到前端
            String requestString = objectMapper.writeValueAsString(messageModel);
            if (messageModel.getCode() == 1) {
                // 将 JSON 字符串写入响应流
                response.getWriter().write(requestString);
            } else {
                //失败也将消息返回给页面
                response.getWriter().write(requestString);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
