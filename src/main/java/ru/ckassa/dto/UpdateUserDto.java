package ru.ckassa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserDto {

    @NotNull
    private Long id;
    @NotEmpty
    @Size(min=8, max = 124)
    private String login;
    @NotEmpty
    private String name;
    @NotNull
    private LocalDate dob;
    @NotEmpty @Email
    private String email;
    @NotEmpty
    private String phone;

}
