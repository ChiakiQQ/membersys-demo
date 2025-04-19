package com.caitlyn.membersysdemo.model;

public record Member(
    Long id,
    String username,
    String email,
    String password,
    Long createTime,
    Long deleteTime
) {
}