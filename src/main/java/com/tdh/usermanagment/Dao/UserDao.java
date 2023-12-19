package com.tdh.usermanagment.Dao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdh.usermanagment.cache.TDepartCache;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.jdbc.DBConnection;
import com.tdh.usermanagment.utils.KeyQuery;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * @author : wangshanjie
 * userDao类成员方法连接数据库完成增删改查的操作。
 */
public class UserDao {

    /**
     * 功能：插入单条数据到数据库中
     * @param conn 连接池
     * @param tdhuser 实例化TdhUser类后，接收始化完成的用户对象
     * @return boolean 插入成功返回true，不成功返回false
     */
    public boolean add_user(Connection conn, TdhUser tdhuser) {
        //执行增删改查的语句
        //1、连接数据库
        PreparedStatement ps =null;
        // 预编译sql执行语句
        // todo 修改插入语句，(完成)
        String insertsql = "insert into my_db.t_user(YHID,YHXM,YHKL,YHXB,YHBM,CSRQ,DJSJ,DJRQ,SFJY,PXH) value(?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = conn.prepareStatement(insertsql);
            int i = 1;
            ps.setString(i++, tdhuser.getYHID());
            ps.setString(i++, tdhuser.getYHXM());
            ps.setString(i++, tdhuser.getYHKL());
            ps.setString(i++, tdhuser.getYHXB());
            ps.setString(i++, tdhuser.getYHBM());
            ps.setString(i++, tdhuser.getCSRQ());
            ps.setString(i++, tdhuser.getDJSJ());
            ps.setString(i++, tdhuser.getDJRQ());
            ps.setString(i++, tdhuser.getSFJY());
            ps.setInt(i++, tdhuser.getPXH());
            //执行操作更改
            boolean result = ps.executeUpdate() > 0;
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBConnection.close(ps);
        }
        return false;
    }



    /**
     * 功能：插入单条数据到数据库中
     * @param tdhuser 实例化TdhUser类后，接收始化完成的用户对象
     * @return boolean 插入成功返回true，不成功返回false
     */
    public boolean add_user(TdhUser tdhuser) {
        //执行增删改查的语句
        //1、连接数据库
        Connection conn =null;
        PreparedStatement ps =null;
        // 预编译sql执行语句
        // todo 修改插入语句，(完成)
        String insertsql = "insert into my_db.t_user(YHID,YHXM,YHKL,YHXB,YHBM,CSRQ,DJSJ,DJRQ,SFJY,PXH) value(?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = DBConnection.getConn();
            ps = conn.prepareStatement(insertsql);
            int i = 1;
            ps.setString(i++, tdhuser.getYHID());
            ps.setString(i++, tdhuser.getYHXM());
            ps.setString(i++, tdhuser.getYHKL());
            ps.setString(i++, tdhuser.getYHXB());
            ps.setString(i++, tdhuser.getYHBM());
            ps.setString(i++, tdhuser.getCSRQ());
            ps.setString(i++, tdhuser.getDJSJ());
            ps.setString(i++, tdhuser.getDJRQ());
            ps.setString(i++, tdhuser.getSFJY());
            ps.setInt(i++, tdhuser.getPXH());
            //执行操作更改
            boolean result = ps.executeUpdate() > 0;
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBConnection.close(ps,conn);
        }
        return false;
    }
    /**
     * 功能：删除用户的信息
     * @param YHID String类型：用户id
     * @return boolean 插入成功返回true，不成功返回false
     */
    public boolean delete_user(String YHID) {
        //执行增删改查的语句
        //1、连接数据库
        Connection conn =null;
        PreparedStatement ps = null;
        // 预编译sql执行语句
        String sql = "delete from my_db.t_user where YHID = ?";
        try {
            conn = DBConnection.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,YHID);
            //执行操作更改
            boolean result = ps.executeUpdate() > 0;
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(null, ps, conn);
        }
        return false;
    }

    /**
     * 更新用户的信息
     * @param tdhuser TdhUser类，实例化TdhUser类后，接收始化完成的用户对象
     * @return int 返回插入成功的用户数量
     */
    public int update_user(TdhUser tdhuser) {
        //执行增删改查的语句
        //1、连接数据库
        Connection conn = null;
        int count = 0;
        PreparedStatement ps =null;
        // 预编译sql执行语句
        String sql = "update my_db.t_user set YHXM=?,YHKL = ?, YHXB= ?,YHBM= ?,CSRQ =?,DJSJ = ?,DJRQ=?,SFJY=?,PXH=? where YHID=?";
        try {
            //todo 修改字段设置自增(完成)
            int i =1;
            conn = DBConnection.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(i++, tdhuser.getYHXM());
            ps.setString(i++, tdhuser.getYHKL());
            ps.setString(i++, tdhuser.getYHXB());
            ps.setString(i++, tdhuser.getYHBM());
            ps.setString(i++, tdhuser.getCSRQ());
            ps.setString(i++, tdhuser.getDJSJ());
            ps.setString(i++, tdhuser.getDJRQ());
            ps.setString(i++, tdhuser.getSFJY());
            ps.setInt(i++, tdhuser.getPXH());
            ps.setString(i++, tdhuser.getYHID());
            //执行操作更改
            count =ps.executeUpdate();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(ps, conn);
        }
        return count;
    }


    /**
     * 查询用户的信息(返回单条数据)
     * @param yhid String类 输入用户id
     * @return 根据用户id查询数据，返回查询到user信息。
     */
    public TdhUser query_user(String yhid) {
        //执行增删改查的语句
        //1、连接数据库
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs =null;
        // 预编译sql执行语句
        String sql = "select YHXM,YHKL,YHXB,YHBM,CSRQ,DJSJ,DJRQ,SFJY,PXH from my_db.t_user where YHID=?";
        try {
            conn = DBConnection.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,yhid);
            //执行操作更改！括号中的sql不要传
            rs = ps.executeQuery();
            //创建一个用户对象返回
            while (rs.next()){
                TdhUser tUser = new TdhUser();
                //todo 设置传入字段的时候，不要用序号用字段名称，不然不好区分。(完成)
                tUser.setYHXM(rs.getString("YHXM"));
                tUser.setYHKL(rs.getString("YHKL"));
                tUser.setYHXB(rs.getString("YHXB"));
                tUser.setYHBM(rs.getString("YHBM"));
                tUser.setCSRQ(rs.getString("CSRQ"));
                tUser.setDJSJ(rs.getString("DJSJ"));
                tUser.setDJRQ(rs.getString("DJRQ"));
                tUser.setSFJY(rs.getString("SFJY"));
                tUser.setPXH(rs.getInt("PXH"));
                return tUser;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBConnection.close(rs, ps, conn);
        }
        //如果没有获取到就返回为空
        return null;
    }


    //查询用户的信息(返回列表数据)
    public List<TdhUser> query_AllUser() {

        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs =null;

        String sql = "select YHID,YHXM,YHKL,YHXB,YHBM,CSRQ,DJSJ,DJRQ,SFJY,PXH from my_db.t_user ";
        try {
            conn = DBConnection.getConn();
             ps = conn.prepareStatement(sql);
             rs = ps.executeQuery();
            List<TdhUser> list = new ArrayList<>();
            while (rs.next()){
                TdhUser tUser = new TdhUser();
                tUser.setYHID(rs.getString("YHID"));
                tUser.setYHXM(rs.getString("YHXM"));
                tUser.setYHKL(rs.getString("YHKL"));
                tUser.setYHXB(rs.getString("YHXB"));
                tUser.setYHBM(rs.getString("YHBM"));
                tUser.setCSRQ(rs.getString("CSRQ"));
                tUser.setDJSJ(rs.getString("DJSJ"));
                tUser.setDJRQ(rs.getString("DJRQ"));
                tUser.setSFJY(rs.getString("SFJY"));
                tUser.setPXH(rs.getInt("PXH"));
                list.add(tUser);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBConnection.close(rs, ps, conn);
        }
        //如果没有获取到就返回为空
        return null;
    }











    /**
     * 根据用户姓名或者用户ID 、 用户部门筛选用户
     * 第一个筛选条件是用户ID或者是用户的姓名，第二个筛选条件是用户的部门。
     *        如果这两个条件之一为空，可以根据另一个查询用户
     * @return 返回用户的列表 List<TdhUser>
     */
    public List<TdhUser> query_UserByID_BM(String yhid_xm, String yhbm) {

        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs =null;

        try {
            conn = DBConnection.getConn();
            String sql = "select YHID,YHXM,YHKL,YHXB,YHBM,CSRQ,DJSJ,DJRQ,SFJY,PXH from t_user ";
            List<Object> sqlParams = new ArrayList<>();
            //如果两个参数都不为空，执行这个sql
            if(yhbm!=null && !yhbm.isEmpty()){
                    sql += " and YHBM = ? ";
                sqlParams.add(yhbm);
            }

            if(yhid_xm!=null && !yhid_xm.isEmpty()){
                sql += " and (YHXM like ? or YHID like ?) ";
                sqlParams.add("%"+yhid_xm+"%");
                sqlParams.add("%"+yhid_xm+"%");
            }
            if(true){
                sql += " ORDER BY PXH ASC ";
            }

            ps = conn.prepareStatement(sql.replaceFirst("and", "where"));
            if(!sqlParams.isEmpty()){
                for(int i = 0; i<sqlParams.size(); i++){
                    ps.setObject(i+1,sqlParams.get(i));
                }
            }

            rs = ps.executeQuery();

            List<TdhUser> list = new ArrayList<>();
            while (rs.next()){
                TdhUser tUser = new TdhUser();
                tUser.setYHID(rs.getString("YHID"));
                tUser.setYHXM(rs.getString("YHXM"));
                tUser.setYHKL(rs.getString("YHKL"));
                tUser.setYHXB(rs.getString("YHXB"));
                tUser.setYHBM(rs.getString("YHBM"));
                tUser.setCSRQ(rs.getString("CSRQ"));
                tUser.setDJSJ(rs.getString("DJSJ"));
                tUser.setDJRQ(rs.getString("DJRQ"));
                tUser.setSFJY(rs.getString("SFJY"));
                tUser.setPXH(rs.getInt("PXH"));
                list.add(tUser);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBConnection.close(rs, ps, conn);
        }
        //如果没有获取到就返回为空
        return null;
    }


    /**
     * main方法，用于测试
     * @param args
     */
    public static void main(String[] args) throws JsonProcessingException {


        //测试条件查询
        UserDao userDao = new UserDao();
        List<TdhUser> list = userDao.query_UserByID_BM("12","32010001");
        for(TdhUser tuser: list){
            System.out.println(tuser);
            System.out.println(tuser.getYHID());
        }


//        UserDao userDao = new UserDao();
//        List<TdhUser> list = userDao.query_AllUser();
////        for(TdhUser userr:list){
////            System.out.println(userr.getYHID()+"->"+userr.getYHXM());
////        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String tdhUserListJson = objectMapper.writeValueAsString(list);
//        System.out.println(tdhUserListJson);

//        ObjectMapper objectMapper = new ObjectMapper();
//        List<String> tdhUserListJson = new ArrayList<>();
//        for (TdhUser user : list) {
//            String userJson = objectMapper.writeValueAsString(user);
//            tdhUserListJson.add(userJson);
//        }
//        System.out.println(tdhUserListJson);

        //测试插入
        /*TdhUser user = new TdhUser();
        userDao userdao = new userDao();
        //先把部门和性别信息写入到缓存中
        DepartmentTransform departmentT = new DepartmentTransform();
        GenderTransform genderT = new GenderTransform();
        departmentT.transfromDepartment();
        genderT.getGenderName();
        //随机生成日期
        LocalDate birthdate = RandomBirthdateToString.generateRandomBirthdate();
        //使用SimpleDateFormat格式转换
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //调整日期形式
        String csrq = birthdate.format(formatter);

        // 创建 SimpleDateFormat 对象并指定日期格式为  yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String djsj = sdf1.format(date1);

        // 创建 SimpleDateFormat 对象并指定日期格式为 yyyy-mm-dd
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = new Date();
        String djrq = sdf2.format(date2);

        user.setYHID("9");
        user.setYHXM(RandomStringGenerator.generateRandomString());
        user.setYHKL("man");
        //获得genderNameMap中随机的key
        user.setYHXB(RandomKeyFromMap.getRandomKey(TGenderCache.CODE_YHXB_MAP));
        user.setYHBM(RandomKeyFromMap.getRandomKey(TDepartCache.BMDM_BMMC_MAP));

        user.setCSRQ(csrq);
        user.setDJSJ(djsj);
        user.setDJRQ(djrq);
        user.setSFJY("无");
        user.setPXH(1);
        if(userdao.addUser(user)){
            System.out.println("插入用户成功！");
        }else{
            System.out.println("插入用户失败！");}*/

        //测试更新
        /*//测试更新
        TdhUser user = new TdhUser();
        userDao userdao = new userDao();
        DepartmentTransform departmentT = new DepartmentTransform();
        GenderTransform genderT = new GenderTransform();
        departmentT.transfromDepartment();
        genderT.getGenderName();
        //随机生成日期
        LocalDate birthdate = RandomBirthdateToString.generateRandomBirthdate();
        //使用SimpleDateFormat格式转换
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //调整日期形式
        String csrq = birthdate.format(formatter);

        // 创建 SimpleDateFormat 对象并指定日期格式为  yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String djsj = sdf1.format(date1);

        // 创建 SimpleDateFormat 对象并指定日期格式为 yyyy-mm-dd
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = new Date();
        String djrq = sdf2.format(date2);

        user.setYHXM(RandomStringGenerator.generateRandomString());
        user.setYHKL("man");
        //获得genderNameMap中随机的key
        user.setYHXB(RandomKeyFromMap.getRandomKey(TGenderCache.CODE_YHXB_MAP));
        user.setYHBM(RandomKeyFromMap.getRandomKey(TDepartCache.BMDM_BMMC_MAP));
        user.setYHXM(RandomStringGenerator.generateRandomString());
        user.setYHKL("man");

        //
        user.setCSRQ(csrq);
        user.setDJSJ(djsj);
        user.setDJRQ(djrq);
        user.setSFJY("无");
        user.setPXH(1);
        user.setYHID("8");

        if (userdao.update_user(user)>0) {
            System.out.println("修改用户成功！");
        } else {
            System.out.println("修改用户失败！");
        }*/

        //测试单条用户信息的查询
        /*UserDao userdao = new UserDao();
        TdhUser tUser = userdao.query_user("1");
        //先把部门和性别信息写入到缓存中
        DepartmentTransform departmentT = new DepartmentTransform();
        departmentT.transfromDepartment();

        LocalDate currentDate = LocalDate.now(); //yyyy-MM-dd
        LocalDateTime currentDateTime = LocalDateTime.now();//"yyyy-MM-dd HH:mm:ss"
        // 定义日期格式
        DateTimeFormatter current_djrq = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter current_djsj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 将 LocalDate 对象转换为字符串
        String djrq = currentDate.format(current_djrq);
        String djsj = currentDateTime.format(current_djsj);

        tUser.setDJRQ(djrq);
        tUser.setDJSJ(djsj);

        String yhbm = KeyQuery.keyLookup(tUser.getYHBM(), TDepartCache.BMDM_BMMC_MAP);
        System.out.println("用户部门："+tUser.getYHXM()+
                "用户口令："+tUser.getYHKL()+
                "用户性别："+tUser.getYHXB()+
                "用户部门："+yhbm+
                "出生日期："+tUser.getCSRQ()+
                "登记时间："+tUser.getDJSJ()+
                "登记日期："+tUser.getDJRQ()+
                "是否禁用："+tUser.getSFJY()+
                "排序号"+tUser.getPXH());

        if(tUser.getPXH()%1==0){
            System.out.println("成功！");
        }*/


        //测试删除用户
//        UserDao userdao = new UserDao();
//        if (userdao.delete_user("10000000")) {
//            System.out.println("删除用户成功！");
//        } else {
//            System.out.println("删除用户失败！");
//        }

    }



}
