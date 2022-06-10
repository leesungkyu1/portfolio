package com.reactpractice.lee.dao;

import com.reactpractice.lee.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    void insertBoard(BoardVO boardVO);

    List<BoardVO> getBoard(BoardVO boardVO);

    BoardVO getBoardDetail(int boardKey);

    void updateBoard(BoardVO boardVO);
}
