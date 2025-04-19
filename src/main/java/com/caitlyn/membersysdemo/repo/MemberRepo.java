package com.caitlyn.membersysdemo.repo;

import com.caitlyn.membersysdemo.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insert(Member member) {
        String sql = "INSERT INTO member (username, email, password, create_time) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                member.username(),
                member.email(),
                member.password(),
                member.createTime());
    }
}