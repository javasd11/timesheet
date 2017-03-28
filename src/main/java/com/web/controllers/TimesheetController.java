package com.web.controllers;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.domain.Employee;
import com.domain.Task;
import com.domain.Timesheet;
import com.service.dao.EmployeeDao;
import com.service.dao.TaskDao;
import com.service.dao.TimesheetDao;
import com.web.commands.TimesheetCommand;
import com.web.editors.EmployeeEditor;
import com.web.editors.TaskEditor;

@Controller
@RequestMapping("/timesheets")
public class TimesheetController {
    private TimesheetDao timesheetDao;
    private TaskDao taskDao;
    private EmployeeDao employeeDao;

    @Autowired
    public void setTimesheetDao(TimesheetDao timesheetDao) {
        this.timesheetDao = timesheetDao;
    }

    @Autowired
    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Autowired
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public TimesheetDao getTimesheetDao() {
        return timesheetDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String showTimesheets(Model model){
        List<Timesheet> timesheets = timesheetDao.list();
        model.addAttribute("timesheets", timesheets);
        return "timesheets/list";
    }
    
    @RequestMapping(value="/del{id}", method=RequestMethod.POST)
    public String deleteTimesheet(@PathVariable("id")long id){
        Timesheet toDelete = timesheetDao.find(id);
        timesheetDao.remove(toDelete);
        return "redirect:/timesheets";
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public String getTimesheet(@PathVariable("id") long id, Model model){
        Timesheet timesheet = timesheetDao.find(id);
        TimesheetCommand tsCommand = new TimesheetCommand(timesheet);
        model.addAttribute("tsCommand", tsCommand);
        return "timesheets/view";
    }
   
    /**
 * Updates timesheet with given ID
 * @param id ID of timesheet to lookup from DB
 * @param tsCommand Lightweight command object with changed hours
 * @return redirects to timesheets
 */
   @RequestMapping(value="/{id}", method = RequestMethod.POST)
   public String updateTimesheet(@PathVariable("id") long id,
           @Valid @ModelAttribute("tsCommand") TimesheetCommand tsCommand,
           BindingResult result){
       
       Timesheet timesheet = timesheetDao.find(id);
       if(result.hasErrors()){
           tsCommand.setTimesheet(timesheet);
           return "timesheets/view";
       }
       
       timesheet.setHours(tsCommand.getHours());
       timesheetDao.update(timesheet);
       
       return "redirect:/timesheets";
   }

   @RequestMapping(params="new", method =RequestMethod.GET)
   public String createTimesheetForm(Model model){
       model.addAttribute("timesheet", new Timesheet());
       model.addAttribute("tasks", taskDao.list());
       model.addAttribute("employees",employeeDao.list());
       return "timesheets/new";
   }
   
   @InitBinder
   protected void initBinder(WebDataBinder binder){
       binder.registerCustomEditor(Employee.class, new EmployeeEditor(employeeDao));
       binder.registerCustomEditor(Task.class, new TaskEditor(taskDao));
   }
   
   @RequestMapping(method=RequestMethod.POST)
   public String addTimesheet(Timesheet timesheet){
       timesheetDao.add(timesheet);
       return "redirect:/timesheets";
   }
   
}
