package com.service.dao;

import com.domain.Manager;
import com.service.GenericDao;

/**
 * DAO of Manager.
 */
public interface ManagerDao extends GenericDao<Manager, Long> {
    /**
     * Tries to remove manager from the system.
     * @param manager Manager to remove
     * @return {@code true} if manager is not assigned to any task.
     * Else {@code false}.
     */
    boolean removeManager(Manager manager);
}