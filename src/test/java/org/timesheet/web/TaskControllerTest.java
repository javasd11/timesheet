
package org.timesheet.web;

import com.web.controllers.TaskController;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.timesheet.DomainTestBase;
import com.domain.Department;
import com.domain.Employee;
import com.domain.Manager;
import com.domain.Task;
import com.service.dao.DepartmentDao;
import com.service.dao.EmployeeDao;
import com.service.dao.ManagerDao;
import com.service.dao.TaskDao;
import com.web.exceptions.TaskDeleteException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class TaskControllerTest extends DomainTestBase{

    private Model model;
    
    @Autowired
    private TaskDao taskDao;
    
    @Autowired
    ManagerDao managerDao;
    
    @Autowired
    private EmployeeDao employeeDao;
    
    @Autowired
    private TaskController controller;
    
    @Autowired
    private DepartmentDao departmentDao;
    
    @Before
    public void setUp(){
        model = new ExtendedModelMap();
    }
    
    @After
    public void cleanUp(){
        List<Task>  tasks = taskDao.list();
        for(Task task : tasks){
            taskDao.remove(task);
        }
    }
    
    @Test
    public void testShowTasks(){
        Task  task = sampleTask();
        
        String view = controller.showTasks(model);
        assertEquals("tasks/list", view);
        
        List<Task> listFromDao = taskDao.list();
        Collection<?> listFromModel = (Collection<?>)model.asMap().get("tasks");
        
        assertTrue(listFromModel.contains(task));
        assertTrue(listFromDao.containsAll(listFromModel));
    }

    @Test
    public void testDeleteOk() throws TaskDeleteException{
        Task task = sampleTask();
        long id = task.getId();
        
        String view = controller.deleteTask(id);
        assertEquals("redirect:/tasks", view);
        assertNull(taskDao.find(id));
    }
    
    @Test(expected = TaskDeleteException.class)
    public void testDeleteThrowsException() throws  TaskDeleteException{
        Task  task =sampleTask();
        long id = task.getId();        
        
        TaskDao mockedDao = mock(TaskDao.class);
        when(mockedDao.removeTask(task)).thenReturn(false);
        
        TaskDao originalDao = controller.getTaskDao();
       try{
           controller.setTaskDao(mockedDao);
           controller.deleteTask(id);
       }finally{
           controller.setTaskDao(originalDao);
       }
    }
    
    @Test
    public void testHandleDeleteException(){
        Task task = sampleTask();
        TaskDeleteException e = new TaskDeleteException(task);
        ModelAndView modelAndView = controller.handleDeleteException(e);
        
        assertEquals("tasks/delete-error", modelAndView.getViewName());
        assertTrue(modelAndView.getModelMap().containsValue(task));
    }
    
    @Test
    public void testGetTask(){
        Task task = sampleTask();
        long id = task.getId();
        
        String view = controller.getTask(id, model);
        assertEquals("tasks/view",view);
        assertEquals(task, model.asMap().get("task"));
    }
    
    @Test
    public void testRemoveEmployee(){
        Task task = sampleTask();
        long id = task.getAssignedEmployees().get(0).getId();
        controller.removeEmployee(task.getId(), id);
        
        task = taskDao.find(task.getId());
        
        Employee employee = employeeDao.find(id);
        assertFalse(task.getAssignedEmployees().contains(employee));
    }
    
    @Test
    public void testAdtdEmployee(){
        Task task = sampleTask();
        
        Department department = new Department("test-department");
        departmentDao.add(department);
        
        Employee cassidy = new Employee("Butch Cassidy", department);
        employeeDao.add(cassidy);
        
        controller.addEmployee(task.getId(), cassidy.getId());
        
        task = taskDao.find(task.getId());
        
        Employee employee = employeeDao.find(cassidy.getId());
        assertTrue(task.getAssignedEmployees().contains(employee));
    } 
    
    @Test
    public void testAddTask(){
        Task task = sampleTask();
        
        String view = controller.addTask(task);
        assertEquals("redirect:/tasks", view);
        
        assertEquals(task, taskDao.find(task.getId()));
    }
    
    
    private Task sampleTask() {
       Manager manager = new Manager ("Jesse James");
       managerDao.add(manager);
       
       Department department = new Department("test-department");
        departmentDao.add(department);
       
       Employee terrence = new Employee("Terrence",department);
       Employee kid = new Employee("Sundance Kid", department);
       employeeDao.add(kid);
       employeeDao.add(terrence);
       
       Task task = new Task("Wild West", manager, terrence,kid);
       taskDao.add(task);
       
       return task;
        
    }
    
    
}
