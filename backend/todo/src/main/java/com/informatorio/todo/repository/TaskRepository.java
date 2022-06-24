package com.informatorio.todo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.informatorio.todo.model.BoardModel;
import com.informatorio.todo.model.TaskModel;

public interface TaskRepository extends CrudRepository<TaskModel,Long> {
    public List<TaskModel> findByBoard(BoardModel board);
}
