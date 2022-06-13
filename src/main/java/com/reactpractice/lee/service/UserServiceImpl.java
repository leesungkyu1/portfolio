package com.reactpractice.lee.service;

import com.reactpractice.lee.dao.UserMapper;
import com.reactpractice.lee.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public void registerUser(UserVO userVO) {
        userMapper.insertUser(userVO);
    }
}
