package com.informatorio.todo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String priority;
    private LocalDate creationDate = LocalDate.now();
    private LocalDate finalyDate;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<LabelModel> labels = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private BoardModel board;

    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getPriority() {return priority;}
    public LocalDate getCreationDate() {return creationDate;}
    public LocalDate getFinalyDate() {return finalyDate;}
    public List<LabelModel> getLabels() {return labels;}
    public BoardModel getBoard() {return board;}

    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setPriority(String priority) {this.priority = priority;}
    public LocalDate setFinalyDate(String finalyDate) {
        this.finalyDate = LocalDate.parse(finalyDate);
        return this.finalyDate;
    }
    public void setLabels(LabelModel label) {this.labels.add(label);}
    public void setBoard(BoardModel board) {this.board = board;}

    public TaskModel() {}
    public TaskModel(String title, String description, String priority, String finalyDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.finalyDate = setFinalyDate(finalyDate);
    }
}
