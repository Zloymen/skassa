package ru.ckassa.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Table(name = "users")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(
                name = "findAll",
                procedureName = "get_all_user",
                resultClasses = { User.class },
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, type = void.class)
                }),
        @NamedStoredProcedureQuery(
                name = "insertUser",
                procedureName = "insert_user",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = LocalDate.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = UUID.class)
                }),
        @NamedStoredProcedureQuery(
                name = "updateUser",
                procedureName = "update_user",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = LocalDate.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = String.class)
                }),

        @NamedStoredProcedureQuery(
                name = "deleteUser",
                procedureName = "delete_user",
                parameters = {
                        @StoredProcedureParameter(mode = ParameterMode.OUT, type = Integer.class),
                        @StoredProcedureParameter(mode = ParameterMode.IN, type = Long.class),
                })
})
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String login;

    @Column
    private String name;

    @Column
    private LocalDate dob;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime deleted;

    @Column
    private UUID key;


}
