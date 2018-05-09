package ru.ckassa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserDto {

    @NotEmpty
    @Min(8) @Max(124)
    private String login;
    @NotEmpty
    private String name;
    @NotEmpty
    private LocalDate dob;
    @NotEmpty @Email
    private String email;
    @NotEmpty
    private String phone;
    @NotEmpty
    private UUID key;

}
