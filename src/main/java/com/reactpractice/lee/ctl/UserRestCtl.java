package com.reactpractice.lee.ctl;

import com.reactpractice.lee.jwt.JwtTokenProvider;
import com.reactpractice.lee.security.AccountService;
import com.reactpractice.lee.security.CustomUserDetails;
import com.reactpractice.lee.service.UserService;
import com.reactpractice.lee.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
public class UserRestCtl {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/loginCheck")
    public CustomUserDetails loginCheck(CustomUserDetails userVO, HttpServletResponse response, HttpServletRequest request) throws IOException {

        CustomUserDetails findUser = (CustomUserDetails)accountService.loadUserByUsername(userVO.getId());

        if(findUser != null){
            String jwtToken = jwtTokenProvider.createToken(findUser);
            ResponseCookie cookie = ResponseCookie.from("token", jwtToken).httpOnly(true).sameSite("lax").path("/").build();
            response.addHeader("Set-Cookie", cookie.toString());
        }

        return findUser;
    }


    @PostMapping("/register")
    public HashMap<String, Object> registerUser(@RequestBody UserVO userVO){
        System.out.println("userVO = " + userVO);

        HashMap<String, Object> response = new HashMap<>();
        try{
            userService.registerUser(userVO);
            response.put("code", "200");
        }catch(Exception e){
            e.printStackTrace();
            response.put("code", e.getMessage());
        }
        return response;
    }
}
