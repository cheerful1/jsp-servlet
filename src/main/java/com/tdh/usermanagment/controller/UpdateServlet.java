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

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @author : wangshanjie
 *
 */
@WebServlet("/update_user")
//编写一个类去继承HttpServlet类
public class UpdateServlet extends HttpServlet {
    private final UpdateService updateService = new UpdateService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //所有的请求都跳转到post里面来
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset = utf-8");
        try {
            // 从请求中获取输入流
            InputStream inputStream = request.getInputStream();
            // 使用 Jackson ObjectMapper 将 JSON 数据转化为 TdhUser 对象
            TdhUser tdhUser = objectMapper.readValue(inputStream, TdhUser.class);
            // 调用 insertService 处理用户添加逻辑
            MessageModel addmessageModel = updateService.updateUser(tdhUser);
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
            MessageModel messageModel = new MessageModel();
            messageModel.setCode(0);
            messageModel.setMsg("保存失败！排序号必须是整数！");
            String errorResponseString = objectMapper.writeValueAsString(messageModel);
            response.getWriter().write(errorResponseString);
        }

    }

}
