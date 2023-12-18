package com.tdh.usermanagment.controller;

import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.Dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 根据id查询用户
 * @author : wangshanjie
 * @date :
 */
@WebServlet("/query_user")
//编写一个类去继承HttpServlet类
public class SelectByIdServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //所有的请求都跳转到post里面来
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset = utf-8");

        //request取得所有的值
//        Integer id = Integer.valueOf(request.getParameter("id"));



        String yhid = request.getParameter("yhid");
        //调用添加的接口
        UserDao userdao = new UserDao();
        TdhUser tdhUser = userdao.query_user(yhid);
        request.setAttribute("tdhUserList",tdhUser);
        request.getRequestDispatcher("update.jsp").forward(request,response);







    }

}
