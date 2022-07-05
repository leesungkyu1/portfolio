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
        List<ChatRoom> chatRooms = chatMapper.findAllRoom(chatRoom);
        ChatRoom userList = new ChatRoom();
        if(userList.roomCurrentCount() != null){
            for(int roomKey : userList.roomCurrentCount().keySet()){
                for(int i=0; i<chatRooms.size(); i++){
                    if(chatRooms.get(i).getRoomKey() == roomKey){
                        chatRooms.get(i).setCurrentMemberCount(userList.roomCurrentCount().get(roomKey));
                    }
                }
            }
        }
        return chatRooms;
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
        chatMember.setChatRoomKeyFk(Integer.parseInt((String) map.get("roomKey")));
        chatMember.setType("member");
        ChatMember findMember = chatMapper.findRoomByUserId(chatMember.getUserKeyFk(), chatMember.getChatRoomKeyFk());
        if(findMember == null){
            chatMapper.chatMemberJoin(chatMember);
        }
    }
}
