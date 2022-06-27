package com.informatorio.todo.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.informatorio.todo.model.TaskModel;

@Component
public class TaskView {
    
    private Long id;
    private String title;
    private String description;
    private String priority;
    private String creationDate;
    private String finishDate;
    private List<String> labels;

    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getPriority() {return priority;}
    public String getCreationDate() {return creationDate;}
    public String getFinishDate() {return finishDate;}
    public List<String> getLabels() {return labels;}
    
    public TaskView(){}
    public TaskView(TaskModel task){
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.creationDate = task.getCreationDate().toString();
        this.finishDate = task.getFinishDate().toString();
        this.labels = task.getLabels().stream().map(l -> l.getName()).collect(Collectors.toList());
    }
}
