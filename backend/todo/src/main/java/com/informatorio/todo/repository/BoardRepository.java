package com.informatorio.todo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.informatorio.todo.model.BoardModel;

public interface BoardRepository extends CrudRepository<BoardModel,Long> {
    public List<BoardModel> findAll();
}
