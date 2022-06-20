package com.reactpractice.lee.chat;

import com.google.gson.Gson;
import com.reactpractice.lee.dao.ChatMapper;
import com.reactpractice.lee.dao.UserMapper;
import com.reactpractice.lee.vo.UserVO;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

public class ChatRoom {
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

    public void handleMessage(WebSocketSession session, ChatMessageVO chatMessageVO, ChatMapper chatMapper, UserMapper userMapper) throws IOException{
        List<WebSocketSession> socketList = new ArrayList<>();
        int roomKey = chatMessageVO.getChatRoomId();

        if(chatMessageVO.getType().equals(MessageType.ENTER.toString())){
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
        }else if(chatMessageVO.getType().equals(MessageType.LEAVE.toString())){
            socketList.remove(session);
            roomMap.put(chatMessageVO.getChatRoomId(), socketList);
            chatMessageVO.setMessage(chatMessageVO.getId() + "님이 퇴장하셨습니다.");
            if(roomMap.get(chatMessageVO.getChatRoomId()).isEmpty()){
                roomMap.remove(chatMessageVO.getChatRoomId());
            }
        }
        if(chatMessageVO.getByteBuffer() != null){
            binarySend(chatMessageVO);
        }else{
            send(chatMessageVO , chatMapper, userMapper);
        }

    }


    private void send(ChatMessageVO chatMessageVO, ChatMapper chatMapper, UserMapper userMapper) throws IOException {
            Gson gson = new Gson();
            List<WebSocketSession> roomSessions = roomMap.get(chatMessageVO.getChatRoomId());
            TextMessage textMessage = new TextMessage(gson.toJson(chatMessageVO));
            for(int i=0; i<roomSessions.size(); i++){
                roomSessions.get(i).sendMessage(textMessage);
            }
            UserVO user = userMapper.findUserName(chatMessageVO.getId());
            if(user != null){
                ChatMember member = chatMapper.findRoomByUserId(user.getUserKey());
                chatMessageVO.setChatMemberKeyFk(member.getChatRoomMemberKey());
                chatMapper.createChatLog(chatMessageVO);
            }
    }

    private void binarySend(ChatMessageVO chatMessageVO) throws IOException {
        System.out.println(chatMessageVO.getByteBuffer());
        BinaryMessage binaryMessage = new BinaryMessage(chatMessageVO.getByteBuffer());
        List<WebSocketSession> roomSessions = roomMap.get(chatMessageVO.getChatRoomId());
        for(int i=0; i<roomSessions.size(); i++){
            roomSessions.get(i).sendMessage(binaryMessage);
        }
    }
}
