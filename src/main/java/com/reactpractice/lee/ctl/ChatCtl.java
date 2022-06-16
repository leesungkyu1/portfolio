package com.reactpractice.lee.ctl;

import com.reactpractice.lee.webSocket.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatCtl {

    @GetMapping("/chat/{roomId}")
    public String chat(@PathVariable int roomId) {

        return "chatting";
    }

    @GetMapping("/chat/list")
    public String chatList() {

        return "chatList";
    }
}
