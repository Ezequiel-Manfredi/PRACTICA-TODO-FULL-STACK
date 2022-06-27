package com.informatorio.todo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

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
    public List<TaskView> getAllTasksOfOneBoard(@RequestParam Long board_id){
        Optional<BoardModel> board = boardrepository.findById(board_id);
        if(!board.isPresent()) return null;
        List<TaskModel> tasks = taskrepository.findByBoard(board.get());
        List<TaskView> view = tasks.stream().map(t -> new TaskView(t)).collect(Collectors.toList());
        return view;
    }
    @PostMapping("/tasks") // ?board_id=
    public TaskView createNewTaskOnBoard(@RequestParam Long board_id,@RequestBody QueryTask infoTask){
        TaskModel newTask = new TaskModel(infoTask.getTitle(),
            infoTask.getDescription(),
            infoTask.getPriority(),
            infoTask.getFinishDate());

        Optional<BoardModel> board = boardrepository.findById(board_id);
        if(!board.isPresent()) return null;
        newTask.setBoard(board.get());

        for (String labelStr : infoTask.getLabels()) {
            Optional<LabelModel> label = labelrepository.findByNameIs(labelStr);
            if(!label.isPresent()) {
                label = Optional.of(labelrepository.save(new LabelModel(labelStr)));
            };
            newTask.setLabels(label.get());
        }

        TaskModel task = taskrepository.save(newTask);
        task.getLabels().stream().forEach(l -> {
            l.setTasks(task);
            labelrepository.save(l);
        });
        return new TaskView(task);
    }

    @GetMapping("/tasks/{id}")
    public TaskView getOneTask(@PathVariable("id") Long id){
        TaskModel task = taskrepository.findById(id).get();
        TaskView view = new TaskView(task);
        return view;
    }
}
