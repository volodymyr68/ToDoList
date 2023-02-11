package com.todolist.ToDoList.models;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "todos")
public class ToDos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String task;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
