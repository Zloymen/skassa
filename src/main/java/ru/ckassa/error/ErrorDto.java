package ru.ckassa.error;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorDto {
    private String uuid;
    private String message;

    public ErrorDto(String uuid, String message){
        this.uuid = uuid;
        this.message = message;
    }

}
