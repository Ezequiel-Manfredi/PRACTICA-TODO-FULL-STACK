package com.informatorio.todo.util;

import java.util.List;

public class LabelView {

    private Long id;
    private String name;
    private List<String> tasks;

    public Long getId() {return id;}
    public String getName() {return name;}
    public List<String> getTasks() {return tasks;}

    public LabelView(Long id, String name, List<String> tasks) {
        this.id = id;
        this.name = name;
        this.tasks = tasks;
    }
}
