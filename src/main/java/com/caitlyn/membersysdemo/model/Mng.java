package com.caitlyn.membersysdemo.model;

public record Mng(
        Integer id,
        String name,
        String password,
        Integer sort,
        Integer enable,
        Long createTime
) {}