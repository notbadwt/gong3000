package com.gong3000.user.service;

import com.gong3000.user.entity.User;


public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    User login(String username, String password);

    User saveOrUpdate(User user);

}
