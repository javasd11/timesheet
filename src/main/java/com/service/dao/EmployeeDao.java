package com.service.dao;

import com.domain.Employee;
import com.service.GenericDao;

/**
 * DAO of employee.
 */
public interface EmployeeDao extends GenericDao<Employee, Long> {

    /**
     * Tries to remove employee from the system.
     *
     * @param employee Employee to remove
     * @return {@code true} if employee is not assigned to any task or
     * timesheet. Else {@code false}.
     */
    boolean removeEmployee(Employee employee);

}
