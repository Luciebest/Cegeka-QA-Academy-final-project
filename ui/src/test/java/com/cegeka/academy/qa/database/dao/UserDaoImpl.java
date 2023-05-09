package com.cegeka.academy.qa.database.dao;

import com.cegeka.academy.qa.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao{
    @Autowired
    @Qualifier("usersJdbcTemplate")
    private JdbcTemplate usersJdbcTemplate;
    @Override
    public List<User> getAllUsers() {
        String sqlSelect = "select * from user";
        return usersJdbcTemplate.query(sqlSelect, getUserRowMapper());

    }
    private RowMapper<User> getUserRowMapper() {
        return  (ResultSet rs, int rowNum) -> {
            User user = new User();
            user.setUserName(rs.getString("USERNAME"));
            user.setPassword(rs.getString("PASSWORD"));
            return user;
        };
    }
}
