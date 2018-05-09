package ru.ckassa.service;

import ru.ckassa.dto.CreateUserDto;
import ru.ckassa.dto.DeleteUserDto;
import ru.ckassa.dto.UpdateUserDto;
import ru.ckassa.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    List<User> getAllProcedure();
    Long save(CreateUserDto dto);
    void update(UpdateUserDto dto);
    void delete(Long id);
    User getById(Long id);
}
