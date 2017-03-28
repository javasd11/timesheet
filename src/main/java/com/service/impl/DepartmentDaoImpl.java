package com.service.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.domain.Department;
import com.service.dao.DepartmentDao;

@Repository("departmentDao")
public class DepartmentDaoImpl extends HibernateDao<Department, Long> implements DepartmentDao {

    @Override
    public boolean removeDepartment(Department department) {
        Query employeeQuery = currentSession().createQuery("from Employee e where e.department.id =:id ");
        employeeQuery.setParameter("id", department.getId());

        if (!employeeQuery.list().isEmpty()) {
            return false;
        }

        remove(department);
        return true;

    }
}
