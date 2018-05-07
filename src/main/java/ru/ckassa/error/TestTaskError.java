package ru.ckassa.error;

import lombok.Getter;


public class TestTaskError extends RuntimeException {

    @Getter
    private final ErrorEnum errorEnum;

    public TestTaskError(ErrorEnum errorEnum){
        this.errorEnum = errorEnum;
    }
}
