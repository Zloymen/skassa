package ru.ckassa.error;

import lombok.Getter;

public enum ErrorEnum {
    TEST("test"),
    ;

    @Getter
    private String title;


    ErrorEnum(String title){
        this.title = title;
    }
}
