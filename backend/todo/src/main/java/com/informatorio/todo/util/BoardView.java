package com.informatorio.todo.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.informatorio.todo.model.BoardModel;

@Component
public class BoardView {

    private Long id;
    private String name;
    private List<TaskView> tasks;

    public Long getId() {return id;}
    public String getName() {return name;}
    public List<TaskView> getTasks() {return tasks;}

    public BoardView(){}
    public BoardView(BoardModel board) {
        this.id = board.getId();
        this.name = board.getName();
        this.tasks = board.getTasks().stream().map(t -> new TaskView(t)).collect(Collectors.toList());
    }
}
