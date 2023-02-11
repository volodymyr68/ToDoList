package com.todolist.ToDoList.service;

import com.todolist.ToDoList.models.ToDos;
import com.todolist.ToDoList.models.User;
import com.todolist.ToDoList.repositories.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoService {
    private ToDoRepository  toDoRepository;
    @Autowired
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }
    public List<ToDos> findAll(User user){
        ToDos toDos = new ToDos();
        toDos.setUser(user);
        toDos.setTask("task list is empty");
        if(toDoRepository.findByUser(user).isEmpty()){
            toDoRepository.save(toDos);
            return toDoRepository.findByUser(user);
        }
        return toDoRepository.findByUser(user);
    }
    public void save(ToDos toDos){
        toDoRepository.save(toDos);
    }
    public void delete(Long id){
        toDoRepository.deleteById(id);
    }
    public ToDos findById(Long id){
        return toDoRepository.findById(id).orElse(null);
    }
    public void update(Long id, ToDos toDos){
        ToDos toDosNull = new ToDos();
        ToDos toDosUpdated = new ToDos();
        toDosUpdated = toDoRepository.findById(id).orElse(toDosNull);
        toDosUpdated.setTask(toDos.getTask());
        toDoRepository.save(toDosUpdated);
    }
}
