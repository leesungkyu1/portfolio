package com.reactpractice.lee.ctl;

import com.reactpractice.lee.security.AccountService;
import com.reactpractice.lee.service.UserService;
import com.reactpractice.lee.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
public class UserRestCtl {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @PostMapping("/loginCheck")
    public String loginCheck(){

        return "";
    }

    @PostMapping("/register")
    public HashMap<String, Object> registerUser(UserVO userVO){

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
