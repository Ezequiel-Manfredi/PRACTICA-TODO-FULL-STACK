package com.informatorio.todo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.informatorio.todo.model.LabelModel;

public interface LabelRepository extends CrudRepository<LabelModel,Long> {
    Optional<LabelModel> findByNameIs(String name);
}
