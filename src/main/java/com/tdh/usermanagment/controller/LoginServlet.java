package com.tdh.usermanagment.controller;

import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author : wangshanjie
 * @date : 15:18 2023/12/16
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    /**
     * 重写父类的service方法，处理客户端的登录请求。
     *
     * @param request  HTTP请求对象，包含客户端提交的数据
     * @param response HTTP响应对象，用于向客户端发送响应
     * @throws ServletException 如果发生Servlet异常
     * @throws IOException      如果发生输入或输出异常
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求字符编码为UTF-8
        request.setCharacterEncoding("UTF-8");

        // 1. 接收客户端的请求（接收参数：姓名，密码）
        String uname = request.getParameter("uname");
        String upwd = request.getParameter("upwd");

        // 2. 调用service层的方法返回消息对象
        MessageModel messageModel = userService.userLogin(uname, upwd);

        // 3. 判断消息模型的状态码
        if (messageModel.getCode() == 1) {// 成功
            // 将消息模型对象设置到session作用域中，重定向跳转到登录成功页面
            request.getSession().setAttribute("user", messageModel.getObject());
            // 重定向到首页
            response.sendRedirect(request.getContextPath() + "/display_users");
        } else {// 失败
            // 将消息模型存入request作用域，转发到登录页面显示错误消息
            request.setAttribute("messageModel", messageModel);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}