package com.tdh.usermanagment.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 处理用户注销的Servlet。
 * 映射路径：/LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    /**
     * 处理HTTP POST请求，执行用户注销操作。
     *
     * @param request  HTTP请求对象，包含客户端提交的数据
     * @param response HTTP响应对象，用于向客户端发送响应
     * @throws IOException      如果发生输入或输出异常
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        // 获取当前Session
        HttpSession session = request.getSession(false);
        // 如果Session存在，使其无效
        if (session != null) {
            session.invalidate();
        }

        // 响应成功
        response.getWriter().write("success");
    }
}