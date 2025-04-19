package com.caitlyn.membersysdemo.repo;

import com.caitlyn.membersysdemo.model.Mng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MngRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Mng findByNameAndPassword(String name, String password) {
        String sql = "SELECT * FROM mng WHERE name = ? AND password = ? AND enable = 1 LIMIT 1";
        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return new Mng(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getInt("sort"),
                        rs.getInt("enable"),
                        rs.getLong("create_time")
                );
            }
            return null;
        }, name, password);
    }
}