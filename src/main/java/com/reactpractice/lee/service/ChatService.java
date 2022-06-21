package com.reactpractice.lee.service;


import com.reactpractice.lee.chat.ChatMember;
import com.reactpractice.lee.chat.ChatRoom;

import java.util.HashMap;
import java.util.List;

public interface ChatService {
    List<ChatRoom> findAllRoom(ChatRoom chatRoom);

    int createRoom(String title);

    void chatMemberJoin(ChatMember chatMember);

    void chatMemberJoin(HashMap<String, Object> map);
}
