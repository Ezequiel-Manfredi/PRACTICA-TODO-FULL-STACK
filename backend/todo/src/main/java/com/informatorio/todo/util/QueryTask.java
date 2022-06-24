package com.informatorio.todo.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class QueryTask {
    
    private String title;
    private String description;
    private String priority;
    private String finalyDate;
    private List<String> labels;

    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getPriority() {return priority;}
    public String getFinalyDate() {return finalyDate;}
    public List<String> getLabels() {return labels;}
    
    public QueryTask(){}
    public QueryTask(String title,String description,String priority,String finalyDate,List<String> labels){
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.finalyDate = finalyDate;
        this.labels = labels;
    }
}
