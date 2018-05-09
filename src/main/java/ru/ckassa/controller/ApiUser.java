package ru.ckassa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ckassa.dto.CreateUserDto;
import ru.ckassa.dto.DeleteUserDto;
import ru.ckassa.dto.UpdateUserDto;
import ru.ckassa.entity.User;
import ru.ckassa.error.TestTaskError;
import ru.ckassa.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static ru.ckassa.error.ErrorEnum.TEST;

@RestController
@RequestMapping(value = "/user")
@Validated
@Slf4j
public class ApiUser {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET, consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.getAllProcedure();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Long createUser(@RequestParam CreateUserDto dto) {

        return userService.save(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void updateUser(@RequestParam UpdateUserDto dto) {

        userService.update(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public User getUser(@PathVariable Long id) {

        return userService.getById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void deleteUser(@PathVariable Long id) {

        userService.delete(id);
    }

    @RequestMapping(value = "/error/", method = RequestMethod.GET, consumes = {MediaType.ALL_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public User getError() {
        throw new TestTaskError(TEST);
    }


}