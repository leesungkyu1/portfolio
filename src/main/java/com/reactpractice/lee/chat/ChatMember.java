package com.reactpractice.lee.chat;

public class ChatMember {
    private int chatRoomMemberKey;
    private int userKeyFk;
    private String type;
    private int chatRoomKeyFk;

    public int getChatRoomMemberKey() {
        return chatRoomMemberKey;
    }

    public void setChatRoomMemberKey(int chatRoomMemberKey) {
        this.chatRoomMemberKey = chatRoomMemberKey;
    }

    public int getUserKeyFk() {
        return userKeyFk;
    }

    public void setUserKeyFk(int userKeyFk) {
        this.userKeyFk = userKeyFk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getChatRoomKeyFk() {
        return chatRoomKeyFk;
    }

    public void setChatRoomKeyFk(int chatRoomKeyFk) {
        this.chatRoomKeyFk = chatRoomKeyFk;
    }

    @Override
    public String toString() {
        return "ChatMember{" +
                "chatRoomMemberKey=" + chatRoomMemberKey +
                ", userKeyFk=" + userKeyFk +
                ", type='" + type + '\'' +
                ", chatRoomKeyFk=" + chatRoomKeyFk +
                '}';
    }
}
