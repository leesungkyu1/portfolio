package com.reactpractice.lee.dao;

import com.reactpractice.lee.chat.ChatMember;
import com.reactpractice.lee.chat.ChatMessageVO;
import com.reactpractice.lee.chat.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    ChatRoom findByRoomId(int chatRoomId);

    List<ChatRoom> findAllRoom(ChatRoom chatRoom);

    void createChatLog(ChatMessageVO chatMessageVO);

    ChatMember findRoomByUserId(int userKeyFk, int chatRoomKeyFk);
}
