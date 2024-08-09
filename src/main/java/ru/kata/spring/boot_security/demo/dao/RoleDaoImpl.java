package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.reposiroty.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDaoImpl implements RoleDao {

    private final RoleRepository roleRepository;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public RoleDaoImpl(EntityManager em, RoleRepository roleRepository) {
        this.em = em;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveRole(Role role) {
        em.persist(role);
    }

    @Override
    public Role getRole(String role) {
        return roleRepository.findByRoleName(role);
    }

}