package com.cegeka.academy.qa.database.dao;

import com.cegeka.academy.qa.database.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
}
