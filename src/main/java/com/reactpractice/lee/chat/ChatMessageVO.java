package com.reactpractice.lee.chat;

import org.springframework.web.socket.BinaryMessage;

import java.nio.ByteBuffer;

public class ChatMessageVO {
    private int chatRoomId;
    private String id;
    private String message;
    private String type;
    private int chatMemberKeyFk;
    private String registerDate;
    private boolean fileYn;
    private ByteBuffer byteBuffer;

    public boolean isFileYn() {
        return fileYn;
    }

    public void setFileYn(boolean fileYn) {
        this.fileYn = fileYn;
    }

    public ByteBuffer getByteBuffer() {
        return byteBuffer;
    }

    public void setByteBuffer(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getChatMemberKeyFk() {
        return chatMemberKeyFk;
    }

    public void setChatMemberKeyFk(int chatMemberKeyFk) {
        this.chatMemberKeyFk = chatMemberKeyFk;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "ChatMessageVO{" +
                "chatRoomId=" + chatRoomId +
                ", id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", chatMemberKeyFk=" + chatMemberKeyFk +
                ", registerDate='" + registerDate + '\'' +
                ", fileYn=" + fileYn +
                ", byteBuffer=" + byteBuffer +
                '}';
    }
}
