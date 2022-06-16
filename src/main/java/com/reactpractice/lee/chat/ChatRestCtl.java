package com.reactpractice.lee.chat;

import com.reactpractice.lee.dao.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatRestCtl {

    @Autowired
    private ChatMapper chatMapper;

    @GetMapping("")
    public List<ChatRoom> chatList(ChatRoom chatRoom) {
        System.out.println("진입");
        List<ChatRoom> chatList = chatMapper.findAllRoom(chatRoom);


        return chatList;
    }
}
