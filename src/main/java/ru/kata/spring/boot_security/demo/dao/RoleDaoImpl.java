package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r FROM Role r", Role.class).getResultList();
    }

    @Override
    public Role findByRole(String role) {
        try {
            return entityManager.createQuery("SELECT r FROM Role r WHERE r.role = :role", Role.class)
                    .setParameter("role", role)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Возвращаем null, если роль не найдена
        } catch (Exception e) {
            // Логируем другие ошибки
            System.err.println("Error fetching role: " + e.getMessage());
            throw e; // или обработайте ошибку соответствующим образом
        }
    }
}
