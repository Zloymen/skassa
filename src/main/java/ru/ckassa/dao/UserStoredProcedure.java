package ru.ckassa.dao;

import ru.ckassa.entity.User;

import java.util.List;

public interface UserStoredProcedure {

    List<User> getAllByProcedure();

}


