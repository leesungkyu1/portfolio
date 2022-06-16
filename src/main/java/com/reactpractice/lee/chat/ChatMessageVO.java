package com.reactpractice.lee.chat;

public class ChatMessageVO {
    private int chatRoomId;
    private String id;
    private String message;
    private MessageType type;

    public int getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(int chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ChatMessageVO{" +
                "chatRoomId=" + chatRoomId +
                ", id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
