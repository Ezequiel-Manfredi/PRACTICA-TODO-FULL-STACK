package com.informatorio.todo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "label")
public class LabelModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "labels")
    private List<TaskModel> tasks = new ArrayList<>();

    public Long getId() {return id;}
    public String getName() {return name;}
    public List<TaskModel> getTasks() {return tasks;}
    public void setTasks(TaskModel task) {this.tasks.add(task);}

    public LabelModel() {}
    public LabelModel(String name) {
        this.name = name;
    }
}
