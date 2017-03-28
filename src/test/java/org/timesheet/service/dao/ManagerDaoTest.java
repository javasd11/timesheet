package org.timesheet.service.dao;

import com.service.dao.DepartmentDao;
import com.service.dao.ManagerDao;
import com.service.dao.TaskDao;
import com.service.dao.EmployeeDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.timesheet.DomainTestBase;
import com.domain.Employee;
import com.domain.Manager;
import com.domain.Task;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import com.domain.Department;


public class ManagerDaoTest extends DomainTestBase {
    
    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private TaskDao taskDao;
    
    @Autowired
    private DepartmentDao departmentDao;
    
    @Test
    public void testAdd() {
        int size = managerDao.list().size();
        managerDao.add(new Manager("test-manager"));
        assertTrue (size < managerDao.list().size());
    }

    @Test
    public void testUpdate() {
        Manager manager = new Manager("test-manager");
        managerDao.add(manager);
        manager.setName("updated");

        managerDao.update(manager);
        Manager found = managerDao.find(manager.getId());
        assertEquals("updated", found.getName());
    }

    @Test
    public void testFind() {
        Manager manager = new Manager("test-manager");
        managerDao.add(manager);

        Manager found = managerDao.find(manager.getId());
        assertEquals(found, manager);
    }
    
    @Test
    public void testList() {
        assertEquals(0, managerDao.list().size());
        
        List<Manager> managers = Arrays.asList(
                new Manager("test-1"),
                new Manager("test-2"),
                new Manager("test-3")
        );
        for (Manager manager : managers) {
            managerDao.add(manager);
        }

        List<Manager> found = managerDao.list();
        assertEquals(3, found.size());
        for (Manager manager : found) {
            assertTrue(managers.contains(manager));
        }
    }
    
    @Test
    public void testRemove() {
        Manager manager = new Manager("test-manager");
        managerDao.add(manager);
        
        // successfully added
        assertEquals(manager, managerDao.find(manager.getId()));
        
        // try to remove
        managerDao.remove(manager);
        assertNull(managerDao.find(manager.getId()));
    }

    @Test
    public void testRemoveManager() {
        Manager manager = new Manager("task-manager");
        managerDao.add(manager);

        Department department = new Department("test_department");
        departmentDao.add(department);
        
        Employee employee = new Employee("Jaromir", department);
        employeeDao.add(employee);

        Task task = new Task("test-task", manager, employee);
        taskDao.add(task);

        // try to remove -> shouldn"t work
        assertFalse(managerDao.removeManager(manager));

        // remove task
        taskDao.remove(task);

        // should work -> no more tasks for manager
        assertTrue(managerDao.removeManager(manager));
    }
}