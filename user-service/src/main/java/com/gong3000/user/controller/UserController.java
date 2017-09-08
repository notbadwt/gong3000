package com.gong3000.user.controller;

import com.gong3000.user.entity.User;
import com.gong3000.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping({"/user"})
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping({"/get_by_id"})
    public User getById(Long id) {
        return userService.getById(id);
    }

    @RequestMapping({"/get_by_username"})
    public User getByUsername(String username) {
        return userService.getByUsername(username);
    }

    @RequestMapping({"/create"})
    public User createUser(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }
}
