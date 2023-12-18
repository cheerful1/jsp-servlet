package com.tdh.usermanagment.jdbc;

import java.sql.*;

/**
 * @author : wangshanjie
 * @date : 21:51 2023/12/2
 * 定义一个连接类
 */
public class DBConnection {
    //1、加载jdbc连接mysql的驱动
    public final static String driver = "com.mysql.jdbc.Driver";
    //2、连接mysql数据库的地址
    public final static String url = "jdbc:mysql://localhost:3306/my_db?useSSL=false&characterEncoding=utf8"; //my_db代表的是库的名称
    //3、连接mysql的用户名
    public final static String user = "root";
    //4、连接mysql的密码
    public final static String pwd = "123456";

    //static静态代码块加载jdbc的驱动
    static {
        try {
            //这里选择驱动的名字
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 连接mysql的连接对象
     * @return
     * @throws SQLException
     */
    public static Connection getConn() throws SQLException {
        //获取连接的参数
        return DriverManager.getConnection(url, user, pwd);

    }


    /**
     * 关闭连接，保证mysql资源的释放，能够充分使用资源
     * @param rs 结果集
     * @param ps 预处理
     * @param conn 连接池
     */
    public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            //先关闭结果集
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            //然后关闭预处理
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            //关闭连接池
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * close实现不同的连接的关闭
     * @param ps 预处理
     * @param conn 连接池
     */
    public static void close(PreparedStatement ps, Connection conn) {
        try {
            //关闭预处理
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            //关闭连接池
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * close实现不同的连接的关闭
     * @param ps 预处理
     */
    public static void close(PreparedStatement ps) {
        try {
            //关闭预处理
            if(ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * close实现不同的连接的关闭
     * @param conn 连接池
     */
    public static void close(Connection conn) {
        try {
            //关闭连接池
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
