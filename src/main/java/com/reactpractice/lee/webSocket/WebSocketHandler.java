package com.reactpractice.lee.webSocket;
import com.google.gson.Gson;
import com.reactpractice.lee.chat.ChatMessageVO;
import com.reactpractice.lee.chat.ChatRoom;
import com.reactpractice.lee.chat.MessageType;
import com.reactpractice.lee.dao.ChatMapper;
import com.reactpractice.lee.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.OnClose;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private ChatMapper chatMapper;

    @Autowired
    private UserMapper userMapper;

    private int roomKey;

    private static final String FILE_PATH = "C:/test/file";


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        System.out.println(message.getPayload());
        Gson gson = new Gson();
        ChatMessageVO chatMessage = (ChatMessageVO) gson.fromJson(message.getPayload(), ChatMessageVO.class);
        ChatRoom chatRoom = chatMapper.findByRoomId(chatMessage.getChatRoomId());
        String userName = session.getPrincipal().getName();
        System.out.println(chatMessage.isFileYn());
        if(chatMessage.isFileYn()){
            roomKey = chatMessage.getChatRoomId();
        }
        chatRoom.handleMessage(session, chatMessage, chatMapper, userMapper);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        ChatMessageVO chatMessageVO = new ChatMessageVO();
        ByteBuffer byteBuffer = message.getPayload();
        ChatRoom chatRoom = new ChatRoom();
        try {
            chatMessageVO.setByteBuffer(byteBuffer);
            chatMessageVO.setType(MessageType.CHAT.toString());
            chatMessageVO.setChatRoomId(roomKey);
            chatRoom.handleMessage(session, chatMessageVO, chatMapper, userMapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
