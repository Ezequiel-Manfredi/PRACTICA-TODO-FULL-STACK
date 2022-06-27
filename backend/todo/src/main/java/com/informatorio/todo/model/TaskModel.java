package com.informatorio.todo.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private LocalDate finishDate;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<LabelModel> labels = new HashSet<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private BoardModel board;

    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getPriority() {return priority;}
    public LocalDate getCreationDate() {return creationDate;}
    public LocalDate getFinishDate() {return finishDate;}
    public Set<LabelModel> getLabels() {return labels;}
    public BoardModel getBoard() {return board;}

    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setPriority(String priority) {this.priority = priority;}
    public void setFinishDate(String finishDate) {
        this.finishDate = LocalDate.parse(finishDate);
    }
    public void setLabels(LabelModel label) {this.labels.add(label);}
    public void setBoard(BoardModel board) {this.board = board;}

    public TaskModel() {}
    public TaskModel(String title, String description, String priority, String finishDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        setFinishDate(finishDate);
    }
}
