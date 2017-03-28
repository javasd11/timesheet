
package com.web.editors;

import java.beans.PropertyEditorSupport;
import com.domain.Employee;
import com.service.dao.EmployeeDao;


public class EmployeeEditor extends PropertyEditorSupport{
    
    private EmployeeDao employeeDao;

    public EmployeeEditor(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        long id = Long.parseLong(text);
        Employee employee = employeeDao.find(id);
        setValue(employee);
    }
}
