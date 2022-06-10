package com.reactpractice.lee.ctl;

import com.reactpractice.lee.security.AccountService;
import com.reactpractice.lee.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestCtl {

    @Autowired
    private AccountService accountService;
}
