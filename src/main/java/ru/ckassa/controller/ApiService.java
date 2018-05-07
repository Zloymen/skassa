package ru.ckassa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ckassa.entity.User;
import ru.ckassa.error.TestTaskError;
import ru.ckassa.service.UserService;

import java.util.List;

import static ru.ckassa.error.ErrorEnum.TEST;

@RestController
@RequestMapping(value = "/service")
@Validated
@Slf4j
public class ApiService {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/", method = RequestMethod.GET, consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public List<User> getAllUsers() {
        List<User> list = userService.getAllProcedure();
        return userService.getAll();
    }


    @RequestMapping(value = "/error/", method = RequestMethod.GET, consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public User getError() {
        throw new TestTaskError(TEST);
    }


}