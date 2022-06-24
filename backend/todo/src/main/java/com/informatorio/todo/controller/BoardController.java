package com.informatorio.todo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.informatorio.todo.model.BoardModel;
import com.informatorio.todo.repository.BoardRepository;
import com.informatorio.todo.util.BoardView;

@RestController
public class BoardController {

    private BoardRepository boardrepository;

    @Autowired
    public BoardController(BoardRepository boardrepository) {
        this.boardrepository = boardrepository;
    }

    @GetMapping("/boards")
    public List<BoardView> getAllBoards(){
        List<BoardModel> boards = boardrepository.findAll();
        List<BoardView> view = boards.stream().map(b -> new BoardView(b)).collect(Collectors.toList());
        return view;
    }

    @GetMapping("/boards/{id}")
    public BoardView getOneBoard(@PathVariable("id") Long id) {
        BoardModel board = boardrepository.findById(id).get();
        BoardView view = new BoardView(board);
        return view;
    }
}
