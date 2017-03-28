package org.timesheet.web;

import com.web.controllers.EmployeeController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.timesheet.DomainTestBase;
import com.domain.Employee;
import com.service.dao.EmployeeDao;
import com.web.exceptions.EmployeeDeleteException;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.domain.Department;
import com.service.dao.DepartmentDao;
import static org.mockito.Mockito.mock;


public class EmployeeControllerTest extends DomainTestBase {
    
    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeController controller;
    
    @Autowired
    private DepartmentDao departmentDao;
    
    private Model model; // used for controller
    
    @Before
    public void setUp() {
        model = new ExtendedModelMap();
    }

    @After
    public void cleanUp() {
        List<Employee> employees = employeeDao.list();
        for (Employee employee : employees) {
            employeeDao.remove(employee);
        }
    }

    @Test
    public void testShowEmployees() {
        // prepare some data
        Employee employee = simpleEmployee();
        employeeDao.add(employee);

        // use controller
        String view = controller.showEmployees(model);
        assertEquals("employees/list", view);

        List<Employee> listFromDao = employeeDao.list();
        Collection<?> listFromModel = (Collection<?>) model.asMap().get("employees");

        assertTrue(listFromModel.contains(employee));
        assertTrue(listFromDao.containsAll(listFromModel));
    }
    
    @Test
    public void testDeleteEmployeeOk() throws EmployeeDeleteException {
        // prepare ID to delete
        Employee john =simpleEmployee();
        employeeDao.add(john);
        long id = john.getId();

        // delete & assert
        String view = controller.deleteEmployee(id);
        assertEquals("redirect:/employees", view);
        assertNull(employeeDao.find(id));
    }

    @Test(expected = EmployeeDeleteException.class)
    public void testDeleteEmployeeThrowsException() throws EmployeeDeleteException {
        // prepare ID to delete
        Employee john = simpleEmployee();
        employeeDao.add(john);
        long id = john.getId();

        // mock DAO for this call
        EmployeeDao mockedDao = mock(EmployeeDao.class);
        when(mockedDao.removeEmployee(john)).thenReturn(false);

        EmployeeDao originalDao = controller.getEmployeeDao();
        try {
            // delete & expect exception
            controller.setEmployeeDao(mockedDao);
            controller.deleteEmployee(id);
        } finally {
            controller.setEmployeeDao(originalDao);
        }
    }

    @Test
    public void testHandleDeleteException() {
        Employee john = simpleEmployee();
        EmployeeDeleteException e = new EmployeeDeleteException(john);
        ModelAndView modelAndView = controller.handleDeleteException(e);

        assertEquals("employees/delete-error", modelAndView.getViewName());
        assertTrue(modelAndView.getModelMap().containsValue(john));
    }
    
    @Test
    public void testGetEmployee() {
        // prepare employee
        Employee george = simpleEmployee();
        employeeDao.add(george);
        long id = george.getId();
        
        // get & assert
        String view = controller.getEmployee(id, model);
        assertEquals("employees/view", view);
        assertEquals(george, model.asMap().get("employee"));
        assertEquals(departmentDao.list(),model.asMap().get("departments"));
    }

    @Test
    public void testUpdateEmployee() {
        // prepare employee
        Employee ringo = simpleEmployee();
        employeeDao.add(ringo);
        long id = ringo.getId();

        // user alters Employee in HTML form
        
        Department department = new Department("update-department");
        departmentDao.add(department);
        
        ringo.setName("updateName");
        ringo.setDepartment(department);

        // update & assert
        String view = controller.updateEmployee(id, ringo);
        assertEquals("redirect:/employees", view);
        assertEquals("updateName", employeeDao.find(id).getName());
        assertEquals("update-department",employeeDao.find(id).getDepartment().getName());
    }

    @Test
    public void testAddEmployee() {
        // prepare employee
        Employee paul = simpleEmployee();
        
        // save but via controller
        String view = controller.addEmployee(paul);
        assertEquals("redirect:/employees", view);

        // employee is stored in DB
        assertEquals(paul, employeeDao.find(paul.getId()));
    }
    
     private Employee simpleEmployee() {
        Department department = new Department("test-department");
        departmentDao.add(department);
        return new Employee("test-employee", department);
    }
}