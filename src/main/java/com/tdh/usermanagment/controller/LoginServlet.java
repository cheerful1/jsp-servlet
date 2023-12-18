package com.tdh.usermanagment.controller;

import com.tdh.usermanagment.entity.vo.MessageModel;
import com.tdh.usermanagment.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : wangshanjie
 * @date : 15:18 2023/12/16
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    /**
     * 用户登录
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("UTF-8");
            //1.接收客户端的请求（接收参数：姓名，密码）
            String uname = request.getParameter("uname");
            String upwd = request.getParameter("upwd");
            //2.调用service层的方法返回消息对象
            MessageModel messageModel = userService.userLogin(uname,upwd);
            //3. 判断消息模型的状态码
            if(messageModel.getCode() ==1 ){//成功
                //将消息模型对象设置到session作用域中，重定向跳转到登录成功页面
                request.getSession().setAttribute("user",messageModel.getObject());
                response.sendRedirect("display_users");
            }else{//失败
                request.setAttribute("messageModel",messageModel);
                request.getRequestDispatcher("login.jsp").forward(request,response);
            }
    }
}
