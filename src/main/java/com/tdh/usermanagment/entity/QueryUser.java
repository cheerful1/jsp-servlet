package com.tdh.usermanagment.entity;


/**
 * 作为查询得到的javabean
 */
public class QueryUser {
    /**
     * 用户id或者用户姓名
     */
    private String encodedUserName;
    /**
     * 用户部门
     */
    private String encodeduserDepartment;

    public QueryUser() {
    }

    public QueryUser(String encodedUserName, String encodeduserDepartment) {
        this.encodedUserName = encodedUserName;
        this.encodeduserDepartment = encodeduserDepartment;
    }

    public String getEncodedUserName() {
        return encodedUserName;
    }

    public void setEncodedUserName(String encodedUserName) {
        this.encodedUserName = encodedUserName;
    }

    public String getEncodeduserDepartment() {
        return encodeduserDepartment;
    }

    public void setEncodeduserDepartment(String encodeduserDepartment) {
        this.encodeduserDepartment = encodeduserDepartment;
    }
}
