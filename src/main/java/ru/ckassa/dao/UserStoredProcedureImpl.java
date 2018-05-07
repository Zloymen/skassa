package ru.ckassa.dao;

import ru.ckassa.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

public class UserStoredProcedureImpl  implements UserStoredProcedure{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> getAllByProcedure() {
        StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("findAll");
        return procedureQuery.getResultList();
    }

    /*@Override
    public List<User> getAllByProcedure() {
        StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("findAll");
        return procedureQuery.getResultList();
    }*/

}
