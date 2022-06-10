package com.reactpractice.lee.ctl;


import com.reactpractice.lee.vo.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class MainCtl {

    @GetMapping("/")
    public String main(){

        return "blank";
    }

    @GetMapping("/boards")
    public String boardList(){

        return "tables";
    }

    //글쓰기 화면 반환
    @GetMapping(value = {"/board/{boardKey}", "/board"})
    public String boardDetail(@PathVariable Optional<Integer> boardKey) {
        int num = boardKey.orElse(0);

        System.out.println(num);
        return "insert";
    }
}
