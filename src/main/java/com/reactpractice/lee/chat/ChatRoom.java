package com.reactpractice.lee.chat;

import com.google.gson.Gson;
import com.reactpractice.lee.dao.ChatMapper;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.OnClose;
import java.io.IOException;
import java.util.*;

public class ChatRoom {

    @Autowired
    ChatMapper chatMapper;

    private String roomId;
    private String name;
    private static Map<Integer , List<WebSocketSession>> roomMap = new HashMap<>();

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void handleMessage(WebSocketSession session, ChatMessageVO chatMessageVO) throws IOException{
        List<WebSocketSession> socketList = new ArrayList<>();
        int roomKey = chatMessageVO.getChatRoomId();
        if(chatMessageVO.getType() == MessageType.ENTER){
            if(!roomMap.isEmpty() && roomMap.containsKey(roomKey)){
                for(int i=0; i<roomMap.get(roomKey).size(); i++){
                    if(roomMap.get(roomKey).get(i).getPrincipal().getName().equals(chatMessageVO.getId())){
                        roomMap.get(roomKey).remove(i);
                    }
                }
                socketList.addAll(roomMap.get(roomKey));
            }
            socketList.add(session);
            roomMap.put(roomKey, socketList);
            chatMessageVO.setMessage(chatMessageVO.getId() + "님이 입장하셨습니다.");
        }else if(chatMessageVO.getType() == MessageType.LEAVE){
            socketList.remove(session);
            roomMap.put(chatMessageVO.getChatRoomId(), socketList);
            chatMessageVO.setMessage(chatMessageVO.getId() + "님이 퇴장하셨습니다.");
            if(roomMap.get(chatMessageVO.getChatRoomId()).isEmpty()){
                roomMap.remove(chatMessageVO.getChatRoomId());
            }
        }else{
            chatMessageVO.setMessage(chatMessageVO.getMessage());
        }
        send(chatMessageVO);
    }


    private void send(ChatMessageVO chatMessageVO) throws IOException {
        int index = 0;
        try{
            Gson gson = new Gson();
            List<WebSocketSession> roomSessions = roomMap.get(chatMessageVO.getChatRoomId());
            TextMessage textMessage = new TextMessage(gson.toJson(chatMessageVO));
            for(int i=0; i<roomSessions.size(); i++){
                roomSessions.get(i).sendMessage(textMessage);
                index++;
            }
            chatMapper.createChatLog(chatMessageVO);
        }catch (Exception e){
            roomMap.get(chatMessageVO.getChatRoomId()).remove(index-1);
        }
    }
}
