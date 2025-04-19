package com.caitlyn.membersysdemo.repo;

import com.caitlyn.membersysdemo.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class MemberRowMapper implements org.springframework.jdbc.core.RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Member(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getLong("create_time")
            );
        }
    }

    public void insert(Member member) {
        String sql = "INSERT INTO member (username, email, password, create_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                member.getUsername(),
                member.getEmail(),
                member.getPassword(),
                member.getCreateTime());
    }

    public List<Member> findAll() {
        String sql = "SELECT * FROM member ORDER BY id DESC";
        return jdbcTemplate.query(sql, new MemberRowMapper());
    }

    public void deleteById(long id) {
        String sql = "DELETE FROM member WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Member findById(long id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        List<Member> members = jdbcTemplate.query(sql, new MemberRowMapper(), id);
        return members.isEmpty() ? null : members.get(0);
    }

    public int update(Member member) {
        if (member == null || member.getId() == null) {
            return 0;
        }
        String sql = "UPDATE member SET username = ?, email = ?, password = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                member.getUsername(),
                member.getEmail(),
                member.getPassword(),
                member.getId());
    }

}