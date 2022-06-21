package com.reactpractice.lee.service;

import com.reactpractice.lee.chat.ChatMember;
import com.reactpractice.lee.chat.ChatRoom;
import com.reactpractice.lee.dao.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatMapper chatMapper;

    @Override
    public List<ChatRoom> findAllRoom(ChatRoom chatRoom) {
        return chatMapper.findAllRoom(chatRoom);
    }

    @Override
    public int createRoom(String title) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(title);
        chatMapper.createChatRoom(chatRoom);
        return chatRoom.getRoomKey();
    }

    @Override
    public void chatMemberJoin(ChatMember chatMember) {
        chatMapper.chatMemberJoin(chatMember);
    }

    @Override
    public void chatMemberJoin(HashMap<String, Object> map) {
        ChatMember chatMember = new ChatMember();
        chatMember.setUserKeyFk((int)map.get("userKey"));
        chatMember.setChatRoomKeyFk((int)map.get("roomKey"));
        
    }
}
