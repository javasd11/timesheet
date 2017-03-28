package com.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.domain.Department;
import com.service.dao.DepartmentDao;
import com.web.exceptions.DepartmentDeleteException;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private DepartmentDao departmentDao;

    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }

    @Autowired
    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showDepartments() {
        return new ModelAndView("departments/list", "departments", departmentDao.list());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView getDepartment(@PathVariable("id") long id) {
        return new ModelAndView("departments/view", "department", departmentDao.find(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateDepartment(@PathVariable("id") long id, @ModelAttribute Department department) {
        department.setId(id);
        departmentDao.update(department);
        return "redirect:/departments";
    }

    @RequestMapping(value = "/del{id}", method = RequestMethod.POST)
    public String deleteDepartment(@PathVariable("id") long id) throws DepartmentDeleteException {
        Department toDelete = departmentDao.find(id);
        boolean wasDeleted = departmentDao.removeDepartment(toDelete);
        if (!wasDeleted) {
            throw new DepartmentDeleteException(toDelete);
        }
        return "redirect:/departments";
    }

    @RequestMapping(params = "new", method = RequestMethod.GET)
    public String createDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        return "departments/new";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addDepartment(@ModelAttribute Department department) {
        departmentDao.add(department);
        return "redirect:/departments";
    }

    @ExceptionHandler(DepartmentDeleteException.class)
    public ModelAndView handleDeleteException(DepartmentDeleteException e) {
        ModelMap model = new ModelMap();
        model.put("department", e.getDepartment());
        return new ModelAndView("departments/delete-error", model);
    }
}
