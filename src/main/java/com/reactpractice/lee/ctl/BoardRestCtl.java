package com.reactpractice.lee.ctl;

import com.reactpractice.lee.service.BoardService;
import com.reactpractice.lee.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardRestCtl {

    @Autowired
    private BoardService boardService;


    @PostMapping(value = "/board")
    public String insertBoard(@RequestBody BoardVO boardVO){
        try{
            boardService.insertBoard(boardVO);

            return "200";
        }catch (Exception e){
            e.printStackTrace();
            return "201";
        }
    }

    @GetMapping("/board/list")
    public List<BoardVO> getBoard(BoardVO boardVO){
        List<BoardVO> boardList = boardService.getBoard(boardVO);
        return boardList;
    }


    @GetMapping("/board/{boardKey}")
    public BoardVO boardDetail(@PathVariable("boardKey") int boardKey) {
        BoardVO board = boardService.getBoardDetail(boardKey);
        return board;
    }

    @PutMapping("/board")
    public String updateBoard(@RequestBody BoardVO boardVO){
        System.out.println("boardVO = " + boardVO);

        boardService.updateBoard(boardVO);

        return "200";
    }
}
