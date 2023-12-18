package com.tdh.usermanagment.controller;

import com.tdh.usermanagment.cache.TDepartCache;
import com.tdh.usermanagment.cache.TGenderCache;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.Dao.DepartmentTransform;
import com.tdh.usermanagment.Dao.GenderTransform;
import com.tdh.usermanagment.Dao.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdh.usermanagment.utils.DepartGenderTransformUtil;
import com.tdh.usermanagment.utils.KeyQuery;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;
@WebServlet("/display_users")
//编写一个类去继承HttpServlet类
public class SelectAllUsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //所有的请求都跳转到post里面来
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
//        UserDao userdao = new UserDao();
//        // 查一下数据库
//        List<TdhUser> tdhUserList = userdao.query_AllUser();

//
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String tdhUserListJson = objectMapper.writeValueAsString(tdhUserList);
//        request.setAttribute("tdhUserListJson", tdhUserListJson);
        request.getRequestDispatcher("display_users.jsp").forward(request,response);
    }

}