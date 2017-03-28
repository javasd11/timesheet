package com.web.controllers;

import com.web.exceptions.EmployeeDeleteException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.domain.Department;
import com.domain.Employee;
import com.service.dao.DepartmentDao;
import com.service.dao.EmployeeDao;
import com.web.editors.DepartmentEditor;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeDao employeeDao;
    private DepartmentDao departmentDao;

    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }

    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showEmployees(Model model) {        
        return new ModelAndView ("employees/list", "employees", employeeDao.list());
    }

    @RequestMapping(params = "new", method = RequestMethod.GET)
    public ModelAndView createEmployeeForm() {
        ModelMap model = new ModelMap();
        model.put("employee", new Employee());
        model.put("departments", departmentDao.list());        
        return new ModelAndView("employees/new", model );
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addEmployee(Employee employee) {
        employeeDao.add(employee);
        return "redirect:/employees";
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Department.class, new DepartmentEditor(departmentDao));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getEmployee(@PathVariable("id") long id, Model model) {
        model.addAttribute("employee", employeeDao.find(id));
        model.addAttribute("departments", departmentDao.list());
        return "employees/view";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateEmployee(@PathVariable("id") long id, @ModelAttribute Employee employee) {
        employee.setId(id);
        employeeDao.update(employee);
        return "redirect:/employees";
    }

    /**
     * Deletes employee with specified ID
     *
     * @param id Employee"s ID
     * @return redirects to employees if everything was ok
     * @throws EmployeeDeleteException When employee cannot be deleted
     */
    //@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RequestMapping(value = "/del{id}", method = RequestMethod.POST)
    public String deleteEmployee(@PathVariable("id") long id) throws EmployeeDeleteException {

        Employee toDelete = employeeDao.find(id);

        boolean wasDeleted = employeeDao.removeEmployee(toDelete);

        if (!wasDeleted) {
            throw new EmployeeDeleteException(toDelete);
        }

        // everything OK, see remaining employees
        return "redirect:/employees";
    }

    /**
     * Handles EmployeeDeleteException
     *
     * @param e Thrown exception with employee that couldn"t be deleted
     * @return binds employee to model and returns employees/delete-error
     */
    @ExceptionHandler(EmployeeDeleteException.class)
    public ModelAndView handleDeleteException(EmployeeDeleteException e) {
        ModelMap model = new ModelMap();
        model.put("employee", e.getEmployee());
        return new ModelAndView("employees/delete-error", model);
    }
}
