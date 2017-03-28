
package com.web.exceptions;

import com.domain.Department;


public class DepartmentDeleteException extends  Exception{
    
    Department department;

    public DepartmentDeleteException(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }
    
    

}
