package ru.ckassa.error;

import lombok.Getter;

public enum ErrorEnum {
    TEST("test"),
    DATA_NOT_FOUND("Data not found")
    ;

    @Getter
    private String title;


    ErrorEnum(String title){
        this.title = title;
    }
}
