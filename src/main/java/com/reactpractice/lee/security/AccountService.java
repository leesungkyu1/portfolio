package com.reactpractice.lee.security;

import com.reactpractice.lee.dao.UserMapper;
import com.reactpractice.lee.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        try {
            UserVO member = userMapper.findUserName(username);
            System.out.println(member);
            customUserDetails.setId(username);
            customUserDetails.setPass(member.getPass());
            customUserDetails.setUserKey(member.getUserKey());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return customUserDetails;
    }

    public UserDetails loadUserByUserKey(String userKey) throws UsernameNotFoundException {
        System.out.println(userKey);
        CustomUserDetails user = null;
        user = userMapper.getUserById(Integer.parseInt(userKey));
        user.setUserAuth(user.getAuthorities());

        return user;
    }

}
