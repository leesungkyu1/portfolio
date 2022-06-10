package com.reactpractice.lee.service;

import com.reactpractice.lee.vo.BoardVO;

import java.util.List;

public interface BoardService {

    void insertBoard(BoardVO boardVO);

    List<BoardVO> getBoard(BoardVO boardVO);

    BoardVO getBoardDetail(int boardKey);

    void updateBoard(BoardVO boardVO);
}
