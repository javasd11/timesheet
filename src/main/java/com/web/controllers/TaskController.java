package com.web.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import com.domain.Employee;
import com.domain.Manager;
import com.domain.Task;
import com.service.dao.EmployeeDao;
import com.service.dao.ManagerDao;
import com.service.dao.TaskDao;
import com.web.editors.ManagerEditor;
import com.web.exceptions.TaskDeleteException;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskDao taskDao;
    private EmployeeDao employeeDao;
    private ManagerDao managerDao;

    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public ManagerDao getManagerDao() {
        return managerDao;
    }

    @Autowired
    public void setManagerDao(ManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Manager.class, new ManagerEditor(managerDao));
    }

    @ExceptionHandler(TaskDeleteException.class)
    public ModelAndView handleDeleteException(TaskDeleteException e) {

        ModelMap model = new ModelMap();
        model.put("task", e.getTask());

        return new ModelAndView("tasks/delete-error", model);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showTasks(Model model) {
        model.addAttribute("tasks", taskDao.list());
        return "tasks/list";
    }

    @RequestMapping(value = "/del{id}", method = RequestMethod.POST)
    public String deleteTask(@PathVariable("id") long id) throws TaskDeleteException {

        Task toDelete = taskDao.find(id);

        if (!taskDao.removeTask(toDelete)) {
            throw new TaskDeleteException(toDelete);
        }
        return "redirect:/tasks";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getTask(@PathVariable("id") long id, Model model) {
        Task task = taskDao.find(id);
        model.addAttribute("task", task);

        List<Employee> eployees = employeeDao.list();
        Set<Employee> unassignedEmployees = new HashSet<Employee>();

        for (Employee employee : eployees) {
            if (!task.getAssignedEmployees().contains(employee)) {
                unassignedEmployees.add(employee);
            }
        }
        model.addAttribute("unassigned", unassignedEmployees);
        return "tasks/view";
    }

    @RequestMapping(value = "/{id}/employees/del{employeeId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployee(@PathVariable("id") long taskId, @PathVariable("employeeId") long employeeId) {
        Employee employee = employeeDao.find(employeeId);
        Task task = taskDao.find(taskId);

        task.removeEmployee(employee);
        taskDao.update(task);
    }

    @RequestMapping(value = "/{id}/employees/add{employeeId}", method = RequestMethod.POST)
    public String addEmployee(@PathVariable("id") long taskId, @PathVariable("employeeId") long employeeId) {

        Employee employee = employeeDao.find(employeeId);
        Task task = taskDao.find(taskId);

        task.addEmployee(employee);
        taskDao.update(task);

        return "redirect:/tasks/" + taskId;
    }

    @RequestMapping(params = "new", method = RequestMethod.GET)
    public String createTaskForm(Model model) {
        model.addAttribute("task", new Task());
        List<Manager> managers = managerDao.list();
        model.addAttribute("managers", managers);
        return "tasks/new";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addTask(Task task) {
        List<Employee> employees = reduce(employeeDao.list());

        task.setAssignedEmployees(employees);
        taskDao.add(task);
        return "redirect:/tasks";
    }

    /**
     * Reduces list of employees to some smaller amount. Simulates user
     * interaction.
     *
     * @param employees Employees to reduced
     * @return New list of some employees from original employees list
     */
    private List<Employee> reduce(List<Employee> employees) {
        List<Employee> reduced = new ArrayList<>();
        Random random = new Random();
        int amount = random.nextInt(employees.size() + 1);

        amount = amount > 5 ? 5 : amount;
        for (int i = 0; i < amount; i++) {
            int randomIdx = random.nextInt(employees.size());
            Employee employee = employees.get(randomIdx);
            reduced.add(employee);
            employees.remove(employee);
        }
        return reduced;
    }

}
