package com.web.controllers;

import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.web.helpers.EntityGenerator;

@Controller
@RequestMapping({"/welcome","/" })
public class WelcomeController {

    @Autowired
    private EntityGenerator entityGenerator;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showMenu() {
        return new ModelAndView("index", "today", new Date());
    }

    @PostConstruct
    public void prepareFakeDomain() {
        entityGenerator.deleteDomain();
        entityGenerator.generateDomain();
    }
}
