package com.tdh.usermanagment.entity;

/**
 * @author : wangshanjie
 * @date : 9:52 2023/12/2
 */
public class TdhUser {
    //数据库中的字段和属性
    /**
     * 用户id
     */
    private String YHID;
    /**
     * 用户姓名
     */
    private String YHXM;
    /**
     *用户口令
     */
    private String YHKL;
    /**
     *用户性别
     */
    private String YHXB;
    /**
     * 用户部门
     */
    private String YHBM;
    /**
     * 出生日期
     */
    private String CSRQ;
    /**
     * 登记时间
     */
    private String DJSJ;
    /**
     * 登记日期
     */
    private String DJRQ;
    /**
     * 是否禁用
     */
    private String SFJY;

    /**
     * 排序号
     */
    private int PXH;

    //定义用户实体类的getter和setter方法
    public String getYHID() {
        return YHID;
    }

    public void setYHID(String YHID) {
        this.YHID = YHID;
    }

    public String getYHXM() {
        return YHXM;
    }

    public void setYHXM(String YHXM) {
        this.YHXM = YHXM;
    }

    public String getYHKL() {
        return YHKL;
    }

    public void setYHKL(String YHKL) {
        this.YHKL = YHKL;
    }

    public String getYHXB() {
        return YHXB;
    }

    public void setYHXB(String YHXB) {
        this.YHXB = YHXB;
    }

    public String getYHBM() {
        return YHBM;
    }

    public void setYHBM(String YHBM) {
        this.YHBM = YHBM;
    }

    public String getCSRQ() {
        return CSRQ;
    }

    public void setCSRQ(String CSRQ) {
        this.CSRQ = CSRQ;
    }

    public String getDJSJ() {
        return DJSJ;
    }

    public void setDJSJ(String DJSJ) {
        this.DJSJ = DJSJ;
    }

    public String getDJRQ() {
        return DJRQ;
    }

    public void setDJRQ(String DJRQ) {
        this.DJRQ = DJRQ;
    }

    public String getSFJY() {
        return SFJY;
    }

    public void setSFJY(String SFJY) {
        this.SFJY = SFJY;
    }

    public int getPXH() {
        return PXH;
    }

    public void setPXH(int PXH) {
        this.PXH = PXH;
    }

    //定义有参和无参的构造
    public TdhUser(String YHID, String YHXM, String YHKL, String YHXB, String YHBM, String CSRQ, String DJSJ, String DJRQ, String SFJY, int PXH) {
        this.YHID = YHID;
        this.YHXM = YHXM;
        this.YHKL = YHKL;
        this.YHXB = YHXB;
        this.YHBM = YHBM;
        this.CSRQ = CSRQ;
        this.DJSJ = DJSJ;
        this.DJRQ = DJRQ;
        this.SFJY = SFJY;
        this.PXH = PXH;
    }

    public TdhUser() {
    }
}
