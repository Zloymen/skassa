package ru.ckassa.dao;

import ru.ckassa.dto.CreateUserDto;
import ru.ckassa.dto.UpdateUserDto;
import ru.ckassa.entity.User;

import java.util.List;

public interface UserStoredProcedure {

    List<User> getAllUser();

    Long create(CreateUserDto dto);

    void update(UpdateUserDto dto);

    void delete(Long id);

}


