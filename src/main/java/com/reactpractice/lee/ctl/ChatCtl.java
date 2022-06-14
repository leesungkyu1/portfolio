package com.reactpractice.lee.ctl;

import com.reactpractice.lee.webSocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatCtl {

    @GetMapping("/chat")
    public String chat() {

        return "chatting";
    }
}
