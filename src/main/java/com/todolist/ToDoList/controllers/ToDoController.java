    package com.todolist.ToDoList.controllers;

    import com.todolist.ToDoList.models.ToDos;
    import com.todolist.ToDoList.models.User;
    import com.todolist.ToDoList.service.ToDoService;
    import com.todolist.ToDoList.service.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;

    import java.security.Principal;

    @Controller
    public class ToDoController {
        private final UserService userService;
        private final ToDoService toDoService;
        @Autowired
        public ToDoController(UserService userService, ToDoService toDoService) {
            this.userService = userService;
            this.toDoService = toDoService;
        }

        @GetMapping("/homepage")
        public String homePage(){
            return "home";
        }
        @GetMapping("/signin")
        public String signinPage(@ModelAttribute("user")User user){
            return "registration";
        }
        @PostMapping("/homepage")
        public String create(@ModelAttribute("user")User user){
                userService.registration(user);
            return "redirect:/login";
        }
        @GetMapping("/authenticated/start-page")
        public String startPage(Principal principal, Model model){
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("todos", toDoService.findAll(user));
            return "todos";
        }
        @GetMapping("/authenticated/new")
        public String newToDo(@ModelAttribute("todo") ToDos toDos) {
            return "new";
        }

        @PostMapping("/authenticated/start-page")
        public String create(@ModelAttribute("todo") ToDos toDos,Principal principal) {
            toDos.setUser(userService.findByUsername(principal.getName()));
            toDoService.save(toDos);
            return "redirect:/authenticated/start-page";
        }
        @GetMapping("/authenticated/edit/{id}")
        public String newTask(@PathVariable("id")Long id,Model model){
            model.addAttribute("todo",toDoService.findById(id));
            return "edit";
        }
        @PatchMapping("/authenticated/edit/{id}")
        public String update(@ModelAttribute("todo") ToDos toDos,
                             @PathVariable("id") Long id) {
            toDoService.update(id, toDos);
            return "redirect:/authenticated/start-page";
        }
        @DeleteMapping("/authenticated/delete/{id}")
        public String delete(@PathVariable("id")Long id) {
            toDoService.delete(id);
            return "redirect:/authenticated/start-page";
        }
    }
