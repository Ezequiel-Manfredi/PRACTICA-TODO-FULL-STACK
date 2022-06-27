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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.informatorio.todo.error.BoardNotFoundException;
import com.informatorio.todo.error.TaskNotFoundException;
import com.informatorio.todo.model.BoardModel;
import com.informatorio.todo.model.LabelModel;
import com.informatorio.todo.model.TaskModel;
import com.informatorio.todo.repository.BoardRepository;
import com.informatorio.todo.repository.LabelRepository;
import com.informatorio.todo.repository.TaskRepository;
import com.informatorio.todo.util.QueryTask;
import com.informatorio.todo.util.TaskView;

@RestController
public class TaskController {

    private TaskRepository taskrepository;
    private BoardRepository boardrepository;
    private LabelRepository labelrepository;

    @Autowired
    public TaskController(TaskRepository taskrepository,BoardRepository boardrepository,
        LabelRepository labelrepository) {
        this.taskrepository = taskrepository;
        this.boardrepository = boardrepository;
        this.labelrepository = labelrepository;
    }

    @GetMapping("/tasks") // ?board_id=
    @ResponseStatus(HttpStatus.FOUND)
    public List<TaskView> getAllTasksOfOneBoard(@RequestParam Long board_id){
        Optional<BoardModel> board = boardrepository.findById(board_id);
        if(!board.isPresent()) throw new BoardNotFoundException("board not found");

        List<TaskModel> tasks = taskrepository.findByBoard(board.get());
        if(tasks.isEmpty()) throw new TaskNotFoundException("none task found");

        List<TaskView> tasksView = tasks
            .stream()
            .map(t -> new TaskView(t))
            .collect(Collectors.toList());
        return tasksView;
    }

    @PostMapping("/tasks") // ?board_id=
    @ResponseStatus(HttpStatus.CREATED)
    public TaskView createNewTaskOnBoard(@RequestParam Long board_id,@RequestBody QueryTask infoTask){
        Optional<BoardModel> board = boardrepository.findById(board_id);
        if(!board.isPresent()) throw new BoardNotFoundException("board not found");

        TaskModel newTask = new TaskModel(
            infoTask.getTitle(),
            infoTask.getDescription(),
            infoTask.getPriority(),
            infoTask.getFinishDate()
        );
        newTask.setBoard(board.get());

        for (String labelStr : infoTask.getLabels()) {
            Optional<LabelModel> label = labelrepository.findByNameIs(labelStr);
            if(!label.isPresent()) {
                LabelModel newLabel = labelrepository.save(new LabelModel(labelStr));
                label = Optional.of(newLabel);
            }
            newTask.setLabels(label.get());
        }

        TaskModel task = taskrepository.save(newTask);
        task
            .getLabels()
            .stream()
            .forEach(l -> {
                l.setTasks(task);
                labelrepository.save(l);
            });
        return new TaskView(task);
    }

    @GetMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public TaskView getOneTask(@PathVariable("id") Long id){
        Optional<TaskModel> task = taskrepository.findById(id);
        if(!task.isPresent()) throw new TaskNotFoundException("task not found");

        TaskView taskView = new TaskView(task.get());
        return taskView;
    }

    @PutMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskView modifyOneTask(@PathVariable("id") Long id,@RequestBody QueryTask infoTask){
        Optional<TaskModel> task = taskrepository.findById(id);
        if(!task.isPresent()) throw new TaskNotFoundException("task to modify not found");


        TaskModel newTask = task.get();
        newTask.setTitle(infoTask.getTitle());
        newTask.setDescription(infoTask.getDescription());
        newTask.setPriority(infoTask.getPriority());
        newTask.setFinishDate(infoTask.getFinishDate());
        for (String labelStr : infoTask.getLabels()) {
            Optional<LabelModel> label = labelrepository.findByNameIs(labelStr);
            if(!label.isPresent()) {
                LabelModel newLabel = labelrepository.save(new LabelModel(labelStr));
                label = Optional.of(newLabel);
            }
            newTask.setLabels(label.get());
        }

        taskrepository.save(newTask);
        return new TaskView(newTask);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOneTask(@PathVariable("id") Long id) {
        Optional<TaskModel> task = taskrepository.findById(id);
        if(!task.isPresent()) throw new TaskNotFoundException("task to delete not found");

        taskrepository.delete(task.get());
    }
}
