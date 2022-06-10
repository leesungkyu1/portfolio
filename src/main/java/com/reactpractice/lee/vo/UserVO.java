package com.reactpractice.lee.vo;

public class UserVO {
    private int userKey;
    private String id;
    private String pass;

    public int getUserKey() {
        return userKey;
    }

    public void setUserKey(int userKey) {
        this.userKey = userKey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "userKey=" + userKey +
                ", id='" + id + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
