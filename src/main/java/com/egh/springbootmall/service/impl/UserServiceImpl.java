package com.egh.springbootmall.service.impl;

import com.egh.springbootmall.dao.UserDao;
import com.egh.springbootmall.dto.UserRegisterRequest;
import com.egh.springbootmall.model.User;
import com.egh.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService
{
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId)
    {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest)
    {
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if (user != null)
        {
            log.warn("該 email {} 已經被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User login(UserRegisterRequest userRegisterRequest)
    {
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if (user == null)
        {
            log.warn("查無此 email {} ", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        String hashPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        if (hashPassword.equals(user.getPassword()))
        {
            return user;
        }

        log.warn("此帳號 {} , 密碼不正確   ", userRegisterRequest.getEmail());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
