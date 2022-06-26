package com.informatorio.todo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.informatorio.todo.error.BoardNotCreatedException;
import com.informatorio.todo.error.BoardNotFoundException;
import com.informatorio.todo.model.BoardModel;
import com.informatorio.todo.repository.BoardRepository;
import com.informatorio.todo.util.BoardView;
import com.informatorio.todo.util.QueryBoard;

@RestController
public class BoardController {

    private BoardRepository boardrepository;

    @Autowired
    public BoardController(BoardRepository boardrepository) {
        this.boardrepository = boardrepository;
    }

    @GetMapping("/boards")
    @ResponseStatus(HttpStatus.FOUND)
    public List<BoardView> getAllBoards(){
        List<BoardModel> boards = boardrepository.findAll();
        if(boards.isEmpty()) throw new BoardNotFoundException("none board found");

        List<BoardView> boardsView = boards
            .stream()
            .map(b -> new BoardView(b))
            .collect(Collectors.toList());
        return boardsView;
    }

    @PostMapping("/boards")
    @ResponseStatus(HttpStatus.CREATED)
    public BoardView createNewBoard(@RequestBody QueryBoard infoBoard){
        Optional<BoardModel> board = boardrepository.findByNameIs(infoBoard.getName());
        if(board.isPresent()) throw new BoardNotCreatedException("board already exists");

        BoardModel newBoard = new BoardModel(infoBoard.getName());
        boardrepository.save(newBoard);
        return new BoardView(newBoard);
    }

    @GetMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public BoardView getOneBoard(@PathVariable("id") Long id) {
        Optional<BoardModel> board = boardrepository.findById(id);
        if(!board.isPresent()) throw new BoardNotFoundException("board not found");

        return new BoardView(board.get());
    }

    @PutMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BoardView modifyOneBoard(@PathVariable("id") Long id,@RequestBody QueryBoard infoBoard){
        Optional<BoardModel> board = boardrepository.findById(id);
        if(!board.isPresent()) throw new BoardNotFoundException("board to modify not found");

        BoardModel newBoard = board.get();
        newBoard.setName(infoBoard.getName());
        boardrepository.save(newBoard);
        return new BoardView(newBoard);
    }

    @DeleteMapping("/boards/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneBoard(@PathVariable("id") Long id) {
        Optional<BoardModel> board = boardrepository.findById(id);
        if(!board.isPresent()) throw new BoardNotFoundException("board to delete not found");

        boardrepository.delete(board.get());
    }
}
