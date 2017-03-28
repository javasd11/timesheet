package org.timesheet.service.dao;

import com.service.dao.DepartmentDao;
import com.service.dao.EmployeeDao;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.timesheet.DomainTestBase;
import com.domain.Department;
import com.domain.Employee;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DepartmentDaoTest extends DomainTestBase {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private EmployeeDao employeeDao;
    
    @Test
    public void testAdd() {
        int size = departmentDao.list().size();
        departmentDao.add(simpleDepartment());
        assertTrue(size < departmentDao.list().size());
    }

    @Test
    public void testUpdate(){
        Department department = simpleDepartment();
        departmentDao.add(department);
        long id = department.getId();
        
        department.setName("updated");
        departmentDao.update(department);
        assertEquals("updated", departmentDao.find(id).getName());
    }
    
    @Test
    public void testFind(){
        Department department = simpleDepartment();
        departmentDao.add(department);
        assertEquals(department, departmentDao.find(department.getId()));
    }
    
    @Test
    public void testList(){
        assertTrue(departmentDao.list().isEmpty());
        
        List<Department> departments = Arrays.asList(
                new Department("test-dep-1"),
                new Department("test-dep-2"),
                new Department("test-dep-2")
        );
        for(Department department:departments){
            departmentDao.add(department);
        }
        
        List<Department> found = departmentDao.list();
        assertEquals(3, found.size());
        
        for(Department department: found){
            assertTrue(departments.contains(department));
        }
        
        assertTrue(departments.containsAll(found));
    }
    
    @Test
    public void testRemove(){
        Department department = simpleDepartment();
        departmentDao.add(department);
        long id = department.getId();
        departmentDao.remove(department);
        assertNull(departmentDao.find(id));        
    }
    
    @Test
    public void testRemoveDepartment(){
        Department department = simpleDepartment();
        departmentDao.add(department);
        
        Employee employee = new Employee("test-employee", department);
        employeeDao.add(employee);
        
        assertFalse(departmentDao.removeDepartment(department));
        
        employeeDao.remove(employee);
        
        assertTrue(departmentDao.removeDepartment(department));
        assertNull(departmentDao.find(department.getId()));
        
    }
    
    private Department simpleDepartment() {
        return new Department("test-department");
    }
}
