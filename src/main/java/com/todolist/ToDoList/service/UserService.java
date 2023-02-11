package com.todolist.ToDoList.service;

import com.todolist.ToDoList.models.Role;
import com.todolist.ToDoList.models.ToDos;
import com.todolist.ToDoList.models.User;
import com.todolist.ToDoList.repositories.ToDoRepository;
import com.todolist.ToDoList.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private ToDoService toDoService;
    private final ToDoRepository toDoRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       ToDoRepository toDoRepository) {
        this.userRepository = userRepository;
        this.toDoRepository = toDoRepository;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void registration(User user){
        User userToBeSaved = new User();
        userToBeSaved.setUsername(user.getUsername());
        userToBeSaved.setEmail(user.getEmail());
        userToBeSaved.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(12)));
        userRepository.save(userToBeSaved);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException(String.format("User '%s' not found",username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role>roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
