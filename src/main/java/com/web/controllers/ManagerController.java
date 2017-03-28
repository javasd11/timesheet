package com.web.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.domain.Employee;
import com.domain.Manager;
import com.service.dao.ManagerDao;
import com.web.exceptions.ManagerDeleteException;

@Controller
@RequestMapping("/managers")
public class ManagerController {

    private ManagerDao managerDao;

    @Autowired
    public void setManagerDao(ManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    public ManagerDao getManagerDao() {
        return managerDao;
    }

    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showManagers() {
        return new ModelAndView ("managers/list","managers", managerDao.list());
    }

    @RequestMapping(params = "new", method = RequestMethod.GET)
    public String createManagerForm(Model model) {
        model.addAttribute("manager", new Employee());
        return "managers/new";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addManager(Manager manager) {
        managerDao.add(manager);
        return "redirect:/managers";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getManager(@PathVariable("id") long id, Model model) {
        Manager manager = managerDao.find(id);
        model.addAttribute("manager", manager);
        return "managers/view";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String updateManager(@PathVariable("id") long id,@ModelAttribute Manager manager) {
        manager.setId(id);
        managerDao.update(manager);
        return "redirect:/managers";
    }

    @RequestMapping(value = "/del{id}", method = RequestMethod.POST)
    public String deleteManager(@PathVariable("id") long id) throws ManagerDeleteException {
        Manager toDelete = managerDao.find(id);
        boolean wasDeleted = managerDao.removeManager(toDelete);
        if (!wasDeleted) {
            throw new ManagerDeleteException(toDelete);
        }
        return "redirect:/managers";
    }

    @ExceptionHandler(ManagerDeleteException.class)
    public ModelAndView handleDeleteException(ManagerDeleteException e) {
        return new ModelAndView("managers/delete-error","manager", e.getManager());
    }
}
