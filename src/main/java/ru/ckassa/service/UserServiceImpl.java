package ru.ckassa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ckassa.dao.UserDao;
import ru.ckassa.entity.User;

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

        return userDao.getAllByProcedure();
    }


}
