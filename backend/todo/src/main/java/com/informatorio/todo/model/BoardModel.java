package com.informatorio.todo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
// import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "board")
public class BoardModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL, orphanRemoval = true)
    // @JoinColumn(name = "board_id")
    private List<TaskModel> tasks = new ArrayList<>();

    public Long getId() {return id;}
    public String getName() {return name;}
    public List<TaskModel> getTasks() {return tasks;}

    public void setName(String name) {this.name = name;}
    public void setTask(TaskModel task) {this.tasks.add(task);}

    public BoardModel() {}
    public BoardModel(String name) {
        this.name = name;
    }
}
