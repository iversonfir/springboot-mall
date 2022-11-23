package com.egh.springbootmall.dao;

import com.egh.springbootmall.dto.UserRegisterRequest;
import com.egh.springbootmall.model.User;

public interface UserDao
{
    User getUserById(Integer userId);
    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserByEmail(String email);
}
