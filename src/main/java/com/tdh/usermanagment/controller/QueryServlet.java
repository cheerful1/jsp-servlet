package com.tdh.usermanagment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdh.usermanagment.entity.QueryUser;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.service.UserService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

/**
 * @author : wangshanjie
 *
 * 主页查询的servlet，主要完成的功能：
 * 1、接收前端传来的两个编码后的字符串，解码赋值到两个变量yhid_xm, yhbm中
 * 2、调用service层的方法将，yhid_xm, yhbm，逻辑判断后，查询数据库
 * 3、接收messageModel对象，并转化为json对象，返回给前端。
 */
@WebServlet("/queryuser")
public class QueryServlet extends HttpServlet {
    private UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    /**
     * 处理HTTP POST请求的方法，用于查询用户信息。
     *
     * @param request HTTP请求对象，包含客户端提交的数据
     * @param response HTTP响应对象，用于向客户端发送响应
     * @throws IOException 如果发生输入或输出异常
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 所有的请求都跳转到post里面来
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try {
            // 接收json流
            InputStream inputStream = request.getInputStream();
            QueryUser quser = objectMapper.readValue(inputStream, QueryUser.class);

            // 1. 接收前端传递的参数。
            String encode_yhid_xm = quser.getEncodedUserName();
            String encode_yhbm = quser.getEncodeduserDepartment();

            // 1. 解码参数
            String decode_yhid_xm = URLDecoder.decode(encode_yhid_xm, "UTF-8");
            String decode_yhbm = URLDecoder.decode(encode_yhbm, "UTF-8");

            // 2. 调用service层的方法返回消息对象
            MessageModel messageModel = userService.queryUser(decode_yhid_xm, decode_yhbm);

            // java对象转化成json，封装到request中返回到前端
            String requestString = objectMapper.writeValueAsString(messageModel);

            // 返回响应
            response.getWriter().write(requestString);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
