package com.tdh.usermanagment.filter;

import com.tdh.usermanagment.entity.TdhUser;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : wangshanjie
 * @date : 10:50 2023/12/17
 */

/**
 * 非法访问拦截
 *      拦截所有的资源 /*
 *   需要放行的资源：
 *          1、指定页面，放行，（无需登录即可访问的页面 例如：登录和注册页面）
 *          2、静态资源，放行，（image,js,css文件）
 *          3、指定操作，放行， （无需登录即可执行的操作 例如登录操作，注册操作）
 *          4、登录状态，放行，（判断session中用户信息是否为空）
 *          其他请求需要被拦截跳转到登录页面
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    /**
     * 该方法在过滤器被初始化时调用，它接收一个 FilterConfig 对象作为参数，可以用于获取过滤器的配置信息。
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 过滤器的主要逻辑部分,处理请求和响应
     * @param servletRequest  用于处理请求
     * @param servletResponse 用于处理响应
     * @param filterChain 用于在过滤器链中传递请求和响应。
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //基于HTTP请求,servletRequest没有提供一些特定于HTTP的方法，例如获取HTTP头、获取请求方法、获取Session等。
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取请求的地址
        String url =request.getRequestURI();
        System.out.println(url);

        //1、指定页面，放行，（无需登录即可访问的页面 例如：登录和注册页面）
        if (url.contains("/login.jsp")){
            filterChain.doFilter(request,response);
            return;
        }
        //2、静态资源，放行，（image,js,css文件）
        if(url.contains("/js")||url.contains("/image")||url.contains("/css")){
            filterChain.doFilter(request,response);
            return;
        }
        //3、指定操作，放行， （无需登录即可执行的操作 例如登录操作，注册操作）
        if (url.contains("/login")){
            filterChain.doFilter(request,response);
            return;
        }
        //4、登录状态，放行，（判断session中用户信息是否为空）
        TdhUser tuser = (TdhUser) request.getSession().getAttribute("user");
        //判断session是否为空
        if(tuser != null){
            filterChain.doFilter(request,response);
            return;
        }
        //其他请求需要被拦截跳转到登录页面
        response.sendRedirect("login.jsp");
    }

    /**
     * 该方法在过滤器被销毁时调用
     */
    @Override
    public void destroy() {

    }
}
