package com.reactpractice.lee.webSocket;
import com.google.gson.Gson;
import com.reactpractice.lee.chat.ChatMessageVO;
import com.reactpractice.lee.chat.ChatRoom;
import com.reactpractice.lee.dao.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Autowired
    ChatMapper chatMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        System.out.println("session = " + session);
        Gson gson = new Gson();
        ChatMessageVO chatMessage = (ChatMessageVO) gson.fromJson(message.getPayload(), ChatMessageVO.class);
        ChatRoom chatRoom = chatMapper.findByRoomId(chatMessage.getChatRoomId());
        String userName = session.getPrincipal().getName();
        chatRoom.handleMessage(session, chatMessage);
    }
}
