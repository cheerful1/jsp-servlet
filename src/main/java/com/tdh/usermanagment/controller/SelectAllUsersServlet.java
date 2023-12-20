package com.tdh.usermanagment.controller;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 显示所有用户的Servlet。
 * 映射路径：/display_users
 * 这个页面直接跳display_users.jsp，
 */
@WebServlet("/display_users")
public class SelectAllUsersServlet extends HttpServlet {

    /**
     * 处理HTTP GET请求，转发到HTTP POST方法处理。
     *
     * @param request  HTTP请求对象，包含客户端提交的数据
     * @param response HTTP响应对象，用于向客户端发送响应
     * @throws IOException      如果发生输入或输出异常
     * @throws ServletException 如果发生Servlet异常
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 调用HTTP POST方法处理GET请求
        doPost(request, response);
    }

    /**
     * 处理HTTP POST请求，跳转到显示所有用户的JSP页面。
     *
     * @param request  HTTP请求对象，包含客户端提交的数据
     * @param response HTTP响应对象，用于向客户端发送响应
     * @throws IOException      如果发生输入或输出异常
     * @throws ServletException 如果发生Servlet异常
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 所有的请求都跳转到post里面来
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        // 转发请求到显示所有用户的JSP页面
        request.getRequestDispatcher("display_users.jsp").forward(request, response);
    }
}