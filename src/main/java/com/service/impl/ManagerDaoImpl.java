package com.service.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.domain.Manager;
import com.service.dao.ManagerDao;

@Repository("managerDao")
public class ManagerDaoImpl extends HibernateDao<Manager, Long> implements ManagerDao {

    @Override
    public boolean removeManager(Manager manager) {
        Query managerQuery = currentSession().createQuery("from Task t where t.manager.id = :id");
        managerQuery.setParameter("id", manager.getId());

        // manager mustn"t be assigned on no task
        if (!managerQuery.list().isEmpty()) {
            return false;
        }

        // ok, remove as usual
        remove(manager);
        return true;
    }
}