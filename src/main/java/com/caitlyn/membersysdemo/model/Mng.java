package com.caitlyn.membersysdemo.model;

public class Mng {
    private Integer id;
    private String name;
    private String password;
    private Integer sort;
    private Integer enable;
    private Long createTime;

    public Mng() {}

    public Mng(Integer id, String name, String password, Integer sort, Integer enable, Long createTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sort = sort;
        this.enable = enable;
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}