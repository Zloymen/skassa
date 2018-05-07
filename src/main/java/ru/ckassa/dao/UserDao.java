package ru.ckassa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ckassa.entity.User;

public interface UserDao extends JpaRepository<User, Long>, UserStoredProcedure {

}
