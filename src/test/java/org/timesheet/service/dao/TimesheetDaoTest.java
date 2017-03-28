package org.timesheet.service.dao;

import com.service.dao.DepartmentDao;
import com.service.dao.ManagerDao;
import com.service.dao.TimesheetDao;
import com.service.dao.TaskDao;
import com.service.dao.EmployeeDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.timesheet.DomainTestBase;
import com.domain.Employee;
import com.domain.Manager;
import com.domain.Task;
import com.domain.Timesheet;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import com.domain.Department;

public class TimesheetDaoTest extends DomainTestBase {

    @Autowired
    private TimesheetDao timesheetDao;

    // daos needed for integration test of timesheetDao
    @Autowired
    private TaskDao taskDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private DepartmentDao departmentDao;

    // common fields for timesheet creation
    private Task task;
    private Employee employee;

//    @Override
//    public void deleteAllDomainEntities() {
//        
//        super.deleteAllDomainEntities();
//        setUp();
//    }

    @Before
    public void setUp() {

        Department department = new Department("test-department");
        departmentDao.add(department);
        employee = new Employee("Steve", department);
        employeeDao.add(employee);

        Manager manager = new Manager("Bob");
        managerDao.add(manager);

        task = new Task("Learn Spring", manager, employee);
        taskDao.add(task);
    }

    @Test
    public void testAdd() {
        int size = timesheetDao.list().size();

        Timesheet timesheet = newTimesheet();
        timesheetDao.add(timesheet);

        assertTrue(size < timesheetDao.list().size());
    }

    @Test
    public void testUpdate() {
        Timesheet timesheet = newTimesheet();
        timesheetDao.add(timesheet);

        // update timesheet
        timesheet.setHours(6);
        taskDao.update(timesheet.getTask());
        timesheetDao.update(timesheet);

        Timesheet found = timesheetDao.find(timesheet.getId());
        assertTrue(6 == found.getHours());
    }

    @Test
    public void testFind() {
        Timesheet timesheet = newTimesheet();
        timesheetDao.add(timesheet);

        assertEquals(timesheet, timesheetDao.find(timesheet.getId()));
    }

    @Test
    public void testList() {
        assertEquals(0, timesheetDao.list().size());
        Timesheet templateTimesheet = newTimesheet();

        List<Timesheet> timesheets = Arrays.asList(
                newTimesheetFromTemplate(templateTimesheet, 4),
                newTimesheetFromTemplate(templateTimesheet, 7),
                newTimesheetFromTemplate(templateTimesheet, 10)
        );
        for (Timesheet timesheet : timesheets) {
            timesheetDao.add(timesheet);
        }

        List<Timesheet> found = timesheetDao.list();
        assertEquals(3, found.size());
        for (Timesheet timesheet : found) {
            assertTrue(timesheets.contains(timesheet));
        }
    }

    @Test
    public void testRemove() {
        Timesheet timesheet = newTimesheet();
        timesheetDao.add(timesheet);

        // successfully added
        assertEquals(timesheet, timesheetDao.find(timesheet.getId()));

        // try to remoce
        timesheetDao.remove(timesheet);
        assertNull(timesheetDao.find(timesheet.getId()));
    }

    /**
     * @return Dummy timesheet for testing
     */
    private Timesheet newTimesheet() {
        return new Timesheet(employee, task, 5);
    }

    private Timesheet newTimesheetFromTemplate(Timesheet template, Integer hours) {
        return new Timesheet(
                template.getWho(),
                template.getTask(),
                hours
        );
    }
}
