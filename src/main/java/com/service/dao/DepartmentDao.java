package com.service.dao;

import com.domain.Department;
import com.service.GenericDao;


public interface DepartmentDao extends GenericDao<Department, Long>{
    /**
     * Tries to remove department from the system.
     *
     * @param department  Department to remove
     * @return {@code true} if departmernt is not assigned to any employee
     *  Else {@code false}.
     */
    boolean removeDepartment(Department department);
}
