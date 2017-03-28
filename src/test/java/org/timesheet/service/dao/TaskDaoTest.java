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

import com.domain.Department;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class TaskDaoTest extends DomainTestBase {
    
    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;
    
//    
//    @Test
//    public void newTest(){
//        Department dep = new Department("new-test-dep");
//        departmentDao.add(dep);
//        
//        Employee em = new Employee("rom", dep);
//        employeeDao.add(em);
//        
//        assertEquals(em, employeeDao.find(em.getId()));
//        assertEquals(dep, employeeDao.find(em.getId()).getDepartment());
//        
//        
////        Task
//                
//    }
    
    
    
    @Test
    public void testAdd() {
        int size = taskDao.list().size();

        Task task = newSpringTask();
        taskDao.add(task);

        assertTrue(size < taskDao.list().size());
        
    }

    @Test
    public void testUpdate() {
        Task task = newSpringTask();
        taskDao.add(task);
        
        // update task
        task.setDescription("Learn Spring 3.1");
        taskDao.update(task);

        Task found = taskDao.find(task.getId());
        assertEquals("Learn Spring 3.1", found.getDescription());
    }

    @Test
    public void testFind() {
        Task task = newSpringTask();
        taskDao.add(task);
        
        assertEquals(task, taskDao.find(task.getId()));
    }
    
    @Test
    public void testList() {
        assertEquals(0, taskDao.list().size());
        Task templateTask = newSpringTask();
        
        List<Task> tasks = Arrays.asList(
                newTaskFromTemplate(templateTask, "1"),
                newTaskFromTemplate(templateTask, "2"),
                newTaskFromTemplate(templateTask, "3")
        );
        for (Task task : tasks) {
            taskDao.add(task);
        }

        List<Task> found = taskDao.list();
        assertEquals(3, found.size());
        for (Task task : found) {
            assertTrue(tasks.contains(task));
        }
    }
    
    @Test
    public void testRemove() {
        Task task = newSpringTask();
        taskDao.add(task);
        
        // successfully added
        assertEquals(task, taskDao.find(task.getId()));
        
        // try to remove
        taskDao.remove(task);
        assertNull(taskDao.find(task.getId()));
    }

    /**
     * @return Dummy task for testing
     */
    private Task newSpringTask() {
        Manager bob = new Manager("Bob");
        managerDao.add(bob);
        
        Department department1 = new Department("test-department-1");
        departmentDao.add(department1);

        Employee steve = new Employee("Steve", department1);
        Employee woz = new Employee("Woz",department1);
        employeeDao.add(steve);
        employeeDao.add(woz);

        return new Task("Learn Spring", bob, steve, woz);
    }

    /**
     * Creates dummy task fo testing as copy of existing task and
     * adds aditional information to every field.
     * @param templateTask Task to copy
     * @param randomInfo Info to append everywhere
     * @return Random task for testing
     */
    private Task newTaskFromTemplate(Task templateTask, String randomInfo) {
        String description = templateTask.getDescription()+ randomInfo;
        
        Manager manager = new Manager(templateTask.getManager().getName());
        managerDao.add(manager);

        List<Employee> templateEmployees = templateTask.getAssignedEmployees();
        Employee[] employees = new Employee[templateEmployees.size()];

        int idx = 0;
        for (Employee templateEmployee : templateEmployees) {
            Employee employee = new Employee(
                    templateEmployee.getName() + randomInfo,
                    templateEmployee.getDepartment());
            employees[idx++] = employee;
            employeeDao.add(employee);
        }

        return new Task(description, manager, employees);
    }
}