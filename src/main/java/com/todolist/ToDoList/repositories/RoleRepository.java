package com.todolist.ToDoList.repositories;

import com.todolist.ToDoList.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
