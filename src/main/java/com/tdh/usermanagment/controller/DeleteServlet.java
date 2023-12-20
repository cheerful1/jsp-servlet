package com.tdh.usermanagment.controller;


import com.tdh.usermanagment.Dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除用户
 * @author : wangshanjie
 * @date :
 */
@WebServlet("/delete_user")
//编写一个类去继承HttpServlet类
public class DeleteServlet extends HttpServlet {
    private final UserDao userDao = new UserDao();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }

    /**
     * 处理HTTP POST请求的方法，用于删除用户数据。
     *
     * @param request HTTP请求对象，包含用户提交的数据
     * @param response HTTP响应对象，用于向客户端发送响应
     * @throws IOException 如果发生输入或输出异常
     * @throws ServletException 如果发生Servlet异常
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //所有的请求都跳转到post里面来
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //request取得用户ID
        String yhid = request.getParameter("yhid");
        try {
            if (yhid == null || "".equals(yhid)){
                response.getWriter().write("删除失败！用户的id为空！");
            }
            //调用userDao的delete_user方法,传入用户ID
            if (userDao.delete_user(yhid)) {
                response.getWriter().write("删除数据成功!");
            } else {
                response.getWriter().write("删除数据失败!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("删除数据失败!数据库发生异常！");
        }
    }
}
