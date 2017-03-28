package org.timesheet.service.dao;

import com.service.dao.DepartmentDao;
import com.service.dao.ManagerDao;
import com.service.dao.TimesheetDao;
import com.service.dao.TaskDao;
import com.service.dao.EmployeeDao;
import java.util.ArrayList;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.timesheet.DomainTestBase;
import com.domain.Employee;
import com.domain.Manager;
import com.domain.Task;
import com.domain.Timesheet;

import java.util.List;

import static org.junit.Assert.*;
import com.domain.Department;


public class EmployeeDaoTest extends DomainTestBase {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TimesheetDao timesheetDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    public void testAdd() {
        int size = employeeDao.list().size();
        employeeDao.add(simpleEmployee());

        // list should have one more employee now
        assertTrue(size < employeeDao.list().size());
    }

    @Test
    public void testUpdate() {
        Employee employee = simpleEmployee();
        employeeDao.add(employee);
        employee.setName("updated");

        employeeDao.update(employee);
        Employee found = employeeDao.find(employee.getId());
        assertEquals("updated", found.getName());
    }

    @Test
    public void testFind() {
        Employee employee = simpleEmployee();
        employeeDao.add(employee);

        Employee found = employeeDao.find(employee.getId());
        assertEquals(found, employee);
    }

    @Test
    public void testList() {
        assertEquals(0, employeeDao.list().size());
        int listSize = 3;
       
        List<Employee> employees = createEmployeesList(listSize);
        List<Employee> found = employeeDao.list();
        assertEquals(listSize, found.size());
        for (Employee employee : found) {
            assertTrue(employees.contains(employee));
        }
    }

    @Test
    public void testRemove() {
        Employee employee = simpleEmployee();
        employeeDao.add(employee);

        // successfully added
        assertEquals(employee, employeeDao.find(employee.getId()));

        // try to remove
        employeeDao.remove(employee);
        assertNull(employeeDao.find(employee.getId()));
    }

    @Test
    public void testRemoveEmployee() {
        Manager manager = new Manager("task-manager");
        managerDao.add(manager);

        Employee employee = simpleEmployee();
        employeeDao.add(employee);

        Task task = new Task("test-task", manager, employee);
        taskDao.add(task);

        Timesheet timesheet = new Timesheet(employee, task, 100);
        timesheetDao.add(timesheet);

        // try to remove -> shouldn"t work
        assertFalse(employeeDao.removeEmployee(employee));

        // remove stuff
        timesheetDao.remove(timesheet);
        taskDao.remove(task);

        // should work -> employee is now free
        assertTrue(employeeDao.removeEmployee(employee));
    }

    private List<Employee> createEmployeesList (int size){
        List<Employee> employees = new ArrayList();
        String name = "testEmployee";
      
        Department department =new Department("test-department");
        departmentDao.add(department);
        
        
        for(int i=0; i<size; i++){
            Employee employee = new Employee(name+i, department);
            employeeDao.add(employee);
            employees.add(employee);
        }
        return employees;
    }
    
    private Employee simpleEmployee() {
        Department department = new Department("test-department");
        departmentDao.add(department);
        return new Employee("test-employee", department);
    }

}
