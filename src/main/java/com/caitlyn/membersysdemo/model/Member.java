package com.caitlyn.membersysdemo.model;

public class Member {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Long createTime;

    public Member() {}

    public Member(Long id, String username, String email, String password, Long createTime) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.createTime = createTime;
    }

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

}