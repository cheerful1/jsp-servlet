package com.tdh.usermanagment.service;
import com.tdh.usermanagment.cache.TDepartCache;
import com.tdh.usermanagment.cache.TGenderCache;
import com.tdh.usermanagment.entity.TdhUser;
import com.tdh.usermanagment.jdbc.DBConnection;
import com.tdh.usermanagment.Dao.DepartmentTransform;
import com.tdh.usermanagment.Dao.GenderTransform;
import com.tdh.usermanagment.Dao.UserDao;
import com.tdh.usermanagment.utils.RandomBirthdateToString;
import com.tdh.usermanagment.utils.RandomKeyFromMap;
import com.tdh.usermanagment.utils.RandomStringGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 单条用户数据和批量用户数据提交数据库。
 */
public class InsertDataToT_USER {

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        //先把部门和性别信息写入到缓存中
        if(TGenderCache.CODE_YHXB_MAP.isEmpty()||TDepartCache.BMDM_BMMC_MAP.isEmpty()){
            DepartmentTransform departmentT = new DepartmentTransform();
            GenderTransform genderT = new GenderTransform();
            departmentT.transfromDepartment();
            genderT.getGenderName();
        }
        try {
            // 获取数据库连接
            connection = DBConnection.getConn();
            // 定义时间范围：[2021年12月1日零点至2022年1月31日晚23时59分59秒]
            LocalDateTime startDate = LocalDateTime.of(2021, 12, 1, 0, 0, 0);
//            LocalDateTime endDate = LocalDateTime.of(2022, 1, 31, 23, 59, 59);
            LocalDateTime endDate = LocalDateTime.of(2021, 12, 1, 0, 1, 0);
            // 设置开始时间
            long startTime = System.currentTimeMillis();
            //选择提交的批量
            int batchsize =1;
            //实例化一个类
            InsertDataToT_USER insertDataToT_user = new InsertDataToT_USER();
            //调用提交函数
            insertDataToT_user.InsertDateBatchsize(startDate, endDate ,batchsize);
            // 设置开结束时间
            long endTime = System.currentTimeMillis();
            System.out.println("数据插入成功.");
            System.out.println("总的执行时间: " + (endTime - startTime) + " milliseconds");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(preparedstatement, connection);
        }
    }

    /**
     * 功能：批量插入数据库的逻辑
     * @param startDate LocalDateTime类 开始时间
     * @param endDate LocalDateTime类 结束时间
     * @param batchSize int类型 提交数据库batch的大小
     */
    private void InsertDateBatchsize(LocalDateTime startDate, LocalDateTime endDate, int batchSize){
        // 获取当前时间
        LocalDateTime currentDateTime = startDate;
        //用户id，使用int来自增
        int i = 10000000;
        int count = 0;
        Connection connection = null;
        try {
            connection = DBConnection.getConn();
            connection.setAutoCommit(false);
            // 循环插入数据直到结束时间
            while (currentDateTime.isBefore(endDate) || currentDateTime.isEqual(endDate)) {
                // 判断单双数日期，设置等待时间
                int seconds = currentDateTime.getDayOfMonth() % 2 == 1 ? 3 : 5;
                TdhUser user = new TdhUser();
                UserDao userDao = new UserDao();
                //设置用户自增的i
                String yhid = String.valueOf(i++);
                // 初始化user对象
                UserInstance(user,yhid);
                //插入数据库
                userDao.add_user(connection, user);
                count++;
                // 提交事务
                if (count % batchSize == 0) {
                    connection.commit();
                }
                // 增加指定的秒数
                currentDateTime = currentDateTime.plusSeconds(seconds);
            }
            // 提交最终事务
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection);
        }
    }

    /**
     * 作用：初始化一个user对象
     * @param user TdhUser类  接收用户对象
     * @param yhid int类型 用户id
     * @return TdhUser类 初始化好的user类对象
     */
    private TdhUser UserInstance(TdhUser user,String yhid){
        //随机生成日期
        LocalDate birthdate = RandomBirthdateToString.generateRandomBirthdate();
        //使用SimpleDateFormat格式转换
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        //调整日期形式
        String csrq = birthdate.format(formatter);

        // 创建 SimpleDateFormat 对象并指定日期格式为  yyyy-MM-dd HH:mm:ss
        SimpleDateFormat datetype1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = new Date();
        String djsj = datetype1.format(date1);

        // 创建 SimpleDateFormat 对象并指定日期格式为 yyyy-mm-dd
        SimpleDateFormat datetype2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date2 = new Date();
        String djrq = datetype2.format(date2);

        user.setYHID(yhid);
        user.setYHXM(RandomStringGenerator.generateRandomString());
        user.setYHKL("123456");
        //todo 获得genderNameMap中随机的key(完成)
        user.setYHXB(RandomKeyFromMap.getRandomKey(TGenderCache.CODE_YHXB_MAP));
        user.setYHBM(RandomKeyFromMap.getRandomKey(TDepartCache.BMDM_BMMC_MAP));
        user.setCSRQ(csrq);
        user.setDJSJ(djsj);
        user.setDJRQ(djrq);
        user.setSFJY("1");
        user.setPXH(1);
        return  user;
    }





}