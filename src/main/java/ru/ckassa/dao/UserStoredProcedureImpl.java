package ru.ckassa.dao;

import ru.ckassa.dto.CreateUserDto;
import ru.ckassa.dto.DeleteUserDto;
import ru.ckassa.dto.UpdateUserDto;
import ru.ckassa.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class UserStoredProcedureImpl  implements UserStoredProcedure{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllUser() {
        StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("findAll");
        return procedureQuery.getResultList();
    }

    @Override
    public Long create(CreateUserDto dto) {
        StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("insertUser");
        procedureQuery.setParameter(2, dto.getLogin());
        procedureQuery.setParameter(3, dto.getName());
        procedureQuery.setParameter(4, dto.getDob());
        procedureQuery.setParameter(5, dto.getEmail());
        procedureQuery.setParameter(6, dto.getPhone());
        procedureQuery.setParameter(7, dto.getKey());
        procedureQuery.execute();
        return (Long) procedureQuery.getOutputParameterValue(1);
    }

    @Override
    public void update(UpdateUserDto dto) {
        StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("updateUser");
        procedureQuery.setParameter(2, dto.getId());
        procedureQuery.setParameter(3, dto.getLogin());
        procedureQuery.setParameter(4, dto.getName());
        procedureQuery.setParameter(5, dto.getDob());
        procedureQuery.setParameter(6, dto.getEmail());
        procedureQuery.setParameter(7, dto.getPhone());
        procedureQuery.execute();
    }

    @Override
    public void delete(Long id) {
        StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("deleteUser");
        procedureQuery.setParameter(2, id);
        procedureQuery.execute();
    }
}
