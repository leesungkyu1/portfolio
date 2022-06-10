package com.reactpractice.lee.service;

import com.reactpractice.lee.dao.BoardMapper;
import com.reactpractice.lee.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;

    public void insertBoard(BoardVO boardVO){
        boardMapper.insertBoard(boardVO);
    }

    @Override
    public List<BoardVO> getBoard(BoardVO boardVO) {
        return boardMapper.getBoard(boardVO);
    }

    @Override
    public BoardVO getBoardDetail(int boardKey) {
        return boardMapper.getBoardDetail(boardKey);
    }

    @Override
    public void updateBoard(BoardVO boardVO) {
        boardMapper.updateBoard(boardVO);
    }
}
