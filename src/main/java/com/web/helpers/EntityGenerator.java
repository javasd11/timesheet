package com.web.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.domain.Employee;
import com.domain.Manager;
import com.domain.Task;
import com.domain.Timesheet;
import com.service.GenericDao;
import com.service.dao.EmployeeDao;
import com.service.dao.ManagerDao;
import com.service.dao.TaskDao;
import com.service.dao.TimesheetDao;

import java.util.List;
import com.domain.Department;
import com.service.dao.DepartmentDao;

/**
 * Small util helper for generating entities to simulate real system.
 */
@Service
public final class EntityGenerator {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ManagerDao managerDao;

    @Autowired
    private TaskDao taskDao;
    
    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private TimesheetDao timesheetDao;

    public void generateDomain() {
        Department design = new Department("Design");
        Department marketing = new Department("Marketing");
        Department programming = new Department("Programming");
        Department beatles = new Department("Beatles");
        addAll(departmentDao, marketing,design,programming, beatles);
                
        Employee steve = new Employee("Steve", design);
        Employee bill = new Employee("Bill", marketing);
        Employee linus = new Employee("Linus", programming);

        // free employees (no tasks/timesheets)
        Employee john = new Employee("John", beatles);
        Employee george = new Employee("George", beatles);
        Employee ringo = new Employee("Ringo", beatles);
        Employee paul = new Employee("Paul", beatles);

        Manager eric = new Manager("Eric");
        Manager larry = new Manager("Larry");

        // free managers
        Manager simon = new Manager("Simon");
        Manager garfunkel = new Manager("Garfunkel");

        addAll(employeeDao, steve, bill, linus, john, george, ringo, paul);
        addAll(managerDao, eric, larry, simon, garfunkel);

        Task springTask = new Task("Migration to Spring 3.1", eric, steve, linus);
        Task tomcatTask = new Task("Optimizing Tomcat", eric, bill);
        Task centosTask = new Task("Deploying to CentOS", larry, linus);

        addAll(taskDao, springTask, tomcatTask, centosTask);

        Timesheet linusOnSpring = new Timesheet(linus, springTask, 42);
        Timesheet billOnTomcat = new Timesheet(bill, tomcatTask, 30);

        addAll(timesheetDao, linusOnSpring, billOnTomcat);
    }

    public void deleteDomain() {
        List<Timesheet> timesheets = timesheetDao.list();
        for (Timesheet timesheet : timesheets) {
            timesheetDao.remove(timesheet);
        }

        List<Task> tasks = taskDao.list();
        for (Task task : tasks) {
            taskDao.remove(task);
        }

        List<Manager> managers = managerDao.list();
        for (Manager manager : managers) {
            managerDao.remove(manager);
        }

        List<Employee> employees = employeeDao.list();
        for (Employee employee : employees) {
            employeeDao.remove(employee);
        }
        
        List<Department>departments = departmentDao.list();
        for(Department department : departments){
            departmentDao.remove(department);
        }
    }

    private <T> void addAll(GenericDao<T, Long> dao, T... entites) {
        for (T o : entites) {
            dao.add(o);
        }
    }
}
