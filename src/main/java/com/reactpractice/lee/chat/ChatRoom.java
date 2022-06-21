package com.reactpractice.lee.chat;

import com.google.gson.Gson;
import com.reactpractice.lee.dao.ChatMapper;
import com.reactpractice.lee.dao.UserMapper;
import com.reactpractice.lee.vo.UserVO;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

public class ChatRoom {
    private int roomKey;
    private String name;
    private List<ChatMember> memberList;
    private static Map<Integer , List<WebSocketSession>> roomMap = new HashMap<>();

    public List<ChatMember> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<ChatMember> memberList) {
        this.memberList = memberList;
    }

    public int getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(int roomKey) {
        this.roomKey = roomKey;
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
        }
        if(chatMessageVO.getByteBuffer() != null){
            binarySend(chatMessageVO);
        }else{
            send(chatMessageVO , chatMapper, userMapper);
        }
        System.out.println("roomMap 진입 = " + roomMap);
    }

    private void send(ChatMessageVO chatMessageVO, ChatMapper chatMapper, UserMapper userMapper) throws IOException {
        System.out.println(chatMessageVO);
        Gson gson = new Gson();
        List<WebSocketSession> roomSessions = roomMap.get(chatMessageVO.getChatRoomId());
        TextMessage textMessage = new TextMessage(gson.toJson(chatMessageVO));
        for(int i=0; i<roomSessions.size(); i++){
            roomSessions.get(i).sendMessage(textMessage);
        }
        UserVO user = userMapper.findUserName(chatMessageVO.getId());
        if(user != null){
            ChatMember member = chatMapper.findRoomByUserId(user.getUserKey(), chatMessageVO.getChatRoomId());
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

    public void handleMessage(WebSocketSession session, CloseStatus status, ChatMapper chatMapper, UserMapper userMapper) throws IOException {
        if(!roomMap.isEmpty()){
            Iterator<Integer> keys = roomMap.keySet().iterator();
            while(keys.hasNext()){
                Integer roomKey = keys.next();
                if(roomMap.get(roomKey).contains(session)){
                    String userName = session.getPrincipal().getName();
                    roomMap.get(roomKey).remove(session);
                    if(roomMap.get(roomKey).isEmpty()){
                        ChatMessageVO chatMessageVO = messageSet(roomKey, "LEAVE", "", userName);
                        messageSave(chatMessageVO, chatMapper, userMapper);
                        roomMap.remove(roomKey);
                    }else{
                        ChatMessageVO chatMessageVO = messageSet(roomKey, "LEAVE", userName+" 님이 퇴장하셨습니다.", userName);
                        send(chatMessageVO, chatMapper, userMapper);
                    }
                    break;
                }
            }
            System.out.println("roomMap 퇴장 = " + roomMap);
        }
    }

    public ChatMessageVO messageSet(int roomId, String type, String message, String userName){
        ChatMessageVO chatMessageVO = new ChatMessageVO();
        chatMessageVO.setType(type);
        chatMessageVO.setChatRoomId(roomId);
        chatMessageVO.setMessage(message);
        chatMessageVO.setId(userName);
        return chatMessageVO;
    }

    public void messageSave(ChatMessageVO chatMessageVO, ChatMapper chatMapper, UserMapper userMapper){
        UserVO user = userMapper.findUserName(chatMessageVO.getId());
        if(user != null){
            ChatMember member = chatMapper.findRoomByUserId(user.getUserKey(), chatMessageVO.getChatRoomId());
            chatMessageVO.setChatMemberKeyFk(member.getChatRoomMemberKey());
            chatMapper.createChatLog(chatMessageVO);
        }
    }
}
