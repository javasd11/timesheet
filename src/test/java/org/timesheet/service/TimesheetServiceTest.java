package org.timesheet.service;

import com.service.TimesheetService;
import com.config.WebAppConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import com.domain.Employee;
import com.domain.Manager;
import com.domain.Task;
import com.service.dao.EmployeeDao;
import com.service.dao.ManagerDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.web.WebAppConfiguration;
import org.timesheet.DomainTestBase;


@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class)
public class TimesheetServiceTest extends AbstractJUnit4SpringContextTests {
    
    @Autowired
    private TimesheetService timesheetService;

   @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private ManagerDao managerDao;

    private final String createScript = "src/main/resources/sql/create-data.sql";
    private final String deleteScript = "src/main/resources/sql/cleanup.sql";

    @Before
    public void insertData() {
        DomainTestBase.executeScript(jdbcTemplate, createScript);
//        SimpleJdbcTestUtils.executeSqlScript(jdbcTemplate,new FileSystemResource(createScript), false);
    }

    @After
    public void cleanUp() {
        DomainTestBase.executeScript(jdbcTemplate, deleteScript);
//        SimpleJdbcTestUtils.executeSqlScript(jdbcTemplate, new FileSystemResource(deleteScript), false);
    }

    @Test
    public void testBusiestTask() {
        Task task = timesheetService.busiestTask();
        assertTrue(1 == task.getId());
    }
    
    @Test
    public void testTasksForEmployees() {
        Employee steve = employeeDao.find(1L);
        Employee bill = employeeDao.find(2L);
        
        assertEquals(2, timesheetService.tasksForEmployee(steve).size());
        assertEquals(1, timesheetService.tasksForEmployee(bill).size());
    }
    
    @Test
    public void testTasksForManagers() {
        Manager eric = managerDao.find(1L);
        assertEquals(1, timesheetService.tasksForManager(eric).size());
    }
    
}