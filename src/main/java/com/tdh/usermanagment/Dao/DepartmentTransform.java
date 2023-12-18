package com.tdh.usermanagment.Dao;

import com.tdh.usermanagment.cache.TDepartCache;
import com.tdh.usermanagment.jdbc.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : wangshanjie
 * @date : 11:08 2023/12/6
 */

/**
 * 部门转换类
 * 包含transfromDepartment()方法：根据性别的部门代码生成相应的性别名称，并生成map
 */
public class DepartmentTransform {
    /**
     * 根据性别的部门代码生成相应的性别名称，并生成map
     * @return DepartmentMap 查询数据库中的BMDM,BMMC字段，返回map，里面是<部门代码，部门名称>
     */
    public void transfromDepartment(){
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs =null;
        //定义sql
        String sql = "select BMDM,BMMC from my_db.t_depart";
        try {
            //获取连接
            conn = DBConnection.getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                String bmdm = rs.getString("BMDM");
                String bmmc = rs.getString("BMMC");
                //用map接收字符串
                TDepartCache.BMDM_BMMC_MAP.put(bmdm, bmmc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(rs,ps, conn);
        }
    }
}
