package com.egh.springbootmall.service;

import com.egh.springbootmall.dto.UserRegisterRequest;
import com.egh.springbootmall.model.User;

public interface UserService
{
    User getUserById(Integer userId);

    Integer register(UserRegisterRequest userRegisterRequest);
}
