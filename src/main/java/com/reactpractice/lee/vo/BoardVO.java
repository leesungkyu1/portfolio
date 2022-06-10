package com.reactpractice.lee.vo;


public class BoardVO {
    private int boardKey;
    private String title;
    private String content;
    private String writer;
    private String openYn;
    private String registerDate;

    public int getBoardKey() {
        return boardKey;
    }

    public void setBoardKey(int boardKey) {
        this.boardKey = boardKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getOpenYn() {
        return openYn;
    }

    public void setOpenYn(String openYn) {
        this.openYn = openYn;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "BoardVO{" +
                "boardKey=" + boardKey +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", openYn='" + openYn + '\'' +
                '}';
    }
}
