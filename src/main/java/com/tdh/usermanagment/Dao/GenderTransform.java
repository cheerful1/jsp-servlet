package com.tdh.usermanagment.Dao;

import com.tdh.usermanagment.cache.TGenderCache;
import com.tdh.usermanagment.jdbc.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : wangshanjie
 * @date : 11:09 2023/12/6
 */

/**
 * 性别名称转换类
 * 包含transfromDepartment()方法：根据性别的code生成相应的性别名称，并生成map
 */
public class GenderTransform {
    /**
     * 根据性别的code生成相应的性别名称，并生成map
     * @return GenderMap,查询数据库中的CODE,MC，两者拼接返回map，里面是<性别代码，性别>
     */
    //todo 改造，参考部门缓存(完成)
    public void getGenderName() {
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        //定义sql
        String sql = "select CODE,MC from my_db.ts_bzdm";
        try {
            conn = DBConnection.getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                String gendercode = rs.getString("CODE");
                String gendermc = rs.getString("MC");
                //用map接收字符串
                TGenderCache.CODE_YHXB_MAP.put(gendercode, gendermc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBConnection.close(rs,ps,conn);
        }
    }
}
