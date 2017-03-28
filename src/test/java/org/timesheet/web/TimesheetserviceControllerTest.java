package org.timesheet.web;

import com.web.controllers.TimesheetServiceController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.timesheet.DomainTestBase;
import com.domain.Employee;
import com.domain.Manager;
import com.domain.Task;
import com.service.TimesheetService;
import com.service.dao.EmployeeDao;
import com.service.dao.ManagerDao;
import com.service.dao.TaskDao;
import static org.junit.Assert.assertEquals;
import org.springframework.jdbc.core.JdbcTemplate;

public class TimesheetserviceControllerTest extends DomainTestBase {

     private Model model;
     
    @Autowired
    private TimesheetService timesheetService;

    @Autowired
    public EmployeeDao employeeDao;
    
    @Autowired
    private ManagerDao managerDao;
    
    @Autowired
    private TaskDao taskDao;
    
    @Autowired
    private TimesheetServiceController controller;
    
   @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final String createScript = "src/main/resources/sql/create-data.sql";
    
    @Before
    public void insertData() {
        executeScript(jdbcTemplate, createScript);
//        SimpleJdbcTestUtils.executeSqlScript(jdbcTemplate,new FileSystemResource(createScript), false);
         model = new ExtendedModelMap();
    }
    
    @Test
    public void testShowMenu(){
        String view = controller.showMenu(model);
        
        assertEquals("timesheet-service/menu",view);
        assertEquals(employeeDao.list(),model.asMap().get("employees"));
        assertEquals(managerDao.list(),model.asMap().get("managers"));
        assertEquals(timesheetService.busiestTask(),((Task) model.asMap().get("busiestTask")));
    }
    
    @Test
    public void testShowManagerTasks(){
        Manager manager = managerDao.list().get(0);
        long id = manager.getId();
        
        String view = controller.showManagerTasks(id, model);
        assertEquals("timesheet-service/manager-tasks",view);
        assertEquals(model.asMap().get("manager"), manager);
        assertEquals(model.asMap().get("tasks"), timesheetService.tasksForManager(manager));
        
    }
    
    @Test 
    public void testShowEmployeeTasks(){
        Employee employee = employeeDao.list().get(0);
        long id = employee.getId();
        
        String view = controller.showEmployeeTask(id, model);
        assertEquals("timesheet-service/employee-tasks", view);
        assertEquals(model.asMap().get("employee"), employee);
        assertEquals(model.asMap().get("tasks"), timesheetService.tasksForEmployee(employee));
    }
    
    
   

}
