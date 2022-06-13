package com.reactpractice.lee.security;

import com.reactpractice.lee.dao.UserMapper;
import com.reactpractice.lee.jwt.JwtTokenProvider;
import com.reactpractice.lee.vo.UserVO;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public UserDetails loadUserByUsername(String username) {
        CustomUserDetails customUserDetails = new CustomUserDetails();
        try {
//            String encryptEmail = aes256.encrypt(username);
            UserVO member = userMapper.findUserName(username);
            System.out.println("member = " + member);
            customUserDetails.setId(username);
            customUserDetails.setPass(member.getPass());

            String jwtToken = jwtTokenProvider.createToken(customUserDetails, member);
            System.out.println("jwtToken = " + jwtToken);
            ResponseCookie cookie = ResponseCookie.from("token", jwtToken).httpOnly(true).sameSite("lax").path("/").build();
            customUserDetails.setToken(jwtToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return customUserDetails;
    }

}
