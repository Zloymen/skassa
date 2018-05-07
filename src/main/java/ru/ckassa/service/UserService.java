package ru.ckassa.service;

import ru.ckassa.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    List<User> getAllProcedure();

}
