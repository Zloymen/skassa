package ru.ckassa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ckassa.dao.UserDao;
import ru.ckassa.dto.CreateUserDto;
import ru.ckassa.dto.DeleteUserDto;
import ru.ckassa.dto.UpdateUserDto;
import ru.ckassa.entity.User;
import ru.ckassa.error.ErrorEnum;
import ru.ckassa.error.TestTaskError;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Transactional
    @Override
    public List<User> getAll(){
        return userDao.findAll();
    }

    @Transactional
    @Override
    public List<User> getAllProcedure(){

        return userDao.getAllUser();
    }

    @Transactional
    @Override
    public Long save(CreateUserDto dto){

        return userDao.create(dto);
    }

    @Transactional
    @Override
    public void update(UpdateUserDto dto){

        userDao.update(dto);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public User getById(Long id) {
        User user = userDao.findById(id).orElse(null);
        if(user == null) throw new TestTaskError(ErrorEnum.DATA_NOT_FOUND);
        return user;
    }
}
