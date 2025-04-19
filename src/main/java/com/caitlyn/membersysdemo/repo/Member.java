package com.caitlyn.membersysdemo.model;

public class Member {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Long createTime;
    private Long deleteTime;

    // MySQL 自動處理的時間欄位
    private java.sql.Timestamp cTime;
    private java.sql.Timestamp uTime;

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Long deleteTime) {
        this.deleteTime = deleteTime;
    }

    public java.sql.Timestamp getcTime() {
        return cTime;
    }

    public void setcTime(java.sql.Timestamp cTime) {
        this.cTime = cTime;
    }

    public java.sql.Timestamp getuTime() {
        return uTime;
    }

    public void setuTime(java.sql.Timestamp uTime) {
        this.uTime = uTime;
    }
}