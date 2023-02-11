package com.todolist.ToDoList.repositories;

import com.todolist.ToDoList.models.ToDos;
import com.todolist.ToDoList.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDos,Long> {
    List<ToDos> findByUser(User user);
}
