
package org.timesheet.web;

import com.web.controllers.TimesheetController;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.timesheet.DomainTestBase;
import com.domain.Department;
import com.domain.Employee;
import com.domain.Manager;
import com.domain.Task;
import com.domain.Timesheet;
import com.service.dao.DepartmentDao;
import com.service.dao.EmployeeDao;
import com.service.dao.ManagerDao;
import com.service.dao.TaskDao;
import com.service.dao.TimesheetDao;
import com.web.commands.TimesheetCommand;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class TimesheetControllerTest extends DomainTestBase{
    private Model model;
    
    @Autowired
    private TimesheetController controller;
    
    @Autowired
    private TimesheetDao timesheetDao;
    @Autowired
    private TaskDao taskDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private ManagerDao managerDao;
    
    @Autowired
    private DepartmentDao departmentDao;
    
    @Before
    public void setUp(){
        model = new ExtendedModelMap();
    }
    
    @After
    public void cleanUp(){
        List<Timesheet>  timesheets = timesheetDao.list();
        for(Timesheet t:timesheets){
            timesheetDao.remove(t);
        }
    }

    @Test
    public void testShowTimesheets(){
        Timesheet timesheet = simpleTimeSheet();
        timesheetDao.add(timesheet);
        assertEquals("timesheets/list", controller.showTimesheets(model));
        
        List<Timesheet> listFromDao = timesheetDao.list();
        Collection<?>listFromModel = (Collection<?>)model.asMap().get("timesheets");
        
        assertTrue(listFromModel.contains(timesheet));
        assertTrue(listFromDao.containsAll(listFromModel));
    }
   
    @Test
    public void testDeketeOk(){
        Timesheet timesheet = simpleTimeSheet();
        timesheetDao.add(timesheet);
        long id=timesheet.getId();
        
        assertEquals("redirect:/timesheets", controller.deleteTimesheet(id));
        assertNull(timesheetDao.find(id));
    }
    
    @Test
    public void testGetTimesheet(){
        Timesheet timesheet = simpleTimeSheet();
        timesheetDao.add(timesheet);
        long id = timesheet.getId();
        
        TimesheetCommand timesheetCommand = new TimesheetCommand(timesheet);
        
        String view = controller.getTimesheet(id, model);
        assertEquals("timesheets/view", view);
        assertEquals(timesheetCommand, model.asMap().get("tsCommand"));
    }
    
    @Test
    public void testUpdateTimesheetValid(){
        Timesheet timesheet = simpleTimeSheet();
        timesheetDao.add(timesheet);
        long id = timesheet.getId();
        
        TimesheetCommand tsCommand = new TimesheetCommand(timesheet);
        tsCommand.setHours(1337);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        
        String view = controller.updateTimesheet(id, tsCommand, result);
        assertEquals("redirect:/timesheets", view);
        assertTrue(1337 == timesheetDao.find(id).getHours());
    }
    
    @Test
    public void testUpdateTimesheetInValid(){
        Timesheet timesheet = simpleTimeSheet();
        timesheetDao.add(timesheet);
        long id = timesheet.getId();
        
        TimesheetCommand tsCommand = new TimesheetCommand(timesheet);
        Integer originalHours = tsCommand.getHours();
        
        tsCommand.setHours(-1);
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);
        
        String view = controller.updateTimesheet(id, tsCommand, result);
        assertEquals("timesheets/view", view);
        assertEquals(originalHours, timesheetDao.find(id).getHours());
    }
    
    @Test
    public void testAddTimesheet(){
        Timesheet timesheet = simpleTimeSheet();
        String view = controller.addTimesheet(timesheet);
        assertEquals("redirect:/timesheets",view );
        
        assertEquals(timesheetDao.find(timesheet.getId()),timesheet); 
    }
    
    private Timesheet simpleTimeSheet(){
        Department department = new Department("test-department");
        departmentDao.add(department);
        
        Employee employee1 = new Employee("Mikel", department);
        employeeDao.add(employee1);
        Employee employee2 = new Employee("Can", department);
        employeeDao.add(employee2);
        
        Manager manager  = new Manager("Roger");
        managerDao.add(manager);
        
        Task task = new Task("Contr",manager,employee1,employee2);
        taskDao.add(task);
        
        return new Timesheet(employee1, task, 20);
    }
    
   
}
