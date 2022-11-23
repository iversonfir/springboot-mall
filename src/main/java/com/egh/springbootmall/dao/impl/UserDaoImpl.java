package com.egh.springbootmall.dao.impl;

import com.egh.springbootmall.dao.UserDao;
import com.egh.springbootmall.dto.UserRegisterRequest;
import com.egh.springbootmall.model.User;
import com.egh.springbootmall.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao
{
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public User getUserById(Integer userId)
    {
        String sql = "SELECT user_id,email,password, created_date , last_modified_date FROM user WHERE user_id=:user_id";
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        List<User> list = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest)
    {
        String sql = "INSERT INTO user (email,password,created_date,last_modified_date)" +
                "VALUES (:email,:password,:created_date,:last_modified_date)";

        HashMap<String, Object> map = new HashMap<>();

        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());

        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int userId = keyHolder.getKey().intValue();
        return userId;
    }

    @Override
    public User getUserByEmail(String email)
    {
        String sql = "select user_id,email,password, created_date , last_modified_date from user where email=:email";
        HashMap<String, Object> map = new HashMap<>();
        map.put("email",email);
        List<User> list = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
        return list.size()>0?list.get(0):null;
    }
}
