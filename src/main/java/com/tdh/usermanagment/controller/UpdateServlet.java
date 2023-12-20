package com.tdh.usermanagment.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdh.usermanagment.cache.TDepartCache;
import com.tdh.usermanagment.cache.TGenderCache;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.Dao.DepartmentTransform;
import com.tdh.usermanagment.Dao.GenderTransform;
import com.tdh.usermanagment.Dao.UserDao;
import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.service.InsertService;
import com.tdh.usermanagment.service.UpdateService;
import com.tdh.usermanagment.utils.KeyQuery;
import com.tdh.usermanagment.utils.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 更新用户信息的Servlet。
 * 映射路径：/update_user
 */
@WebServlet("/update_user")
//编写一个类去继承HttpServlet类
public class UpdateServlet extends HttpServlet {
    private final UpdateService updateService = new UpdateService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }
    /**
     * 处理HTTP POST请求，更新用户信息。
     *
     * @param request  HTTP请求对象，包含客户端提交的数据
     * @param response HTTP响应对象，用于向客户端发送响应
     * @throws IOException      如果发生输入或输出异常
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
            MessageModel addmessageModel = updateService.updateUser(user);
            //java对象转化成json，封装到request中返回到前端
            String requestString = objectMapper.writeValueAsString(addmessageModel);
            if (addmessageModel.getCode()==1) {
                // 将 JSON 字符串写入响应流
                response.getWriter().write(requestString);
            } else {
                //失败也将消息返回给页面
                response.getWriter().write(requestString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
