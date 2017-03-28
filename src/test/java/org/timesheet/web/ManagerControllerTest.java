
package org.timesheet.web;

import com.web.controllers.ManagerController;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.timesheet.DomainTestBase;
import com.domain.Manager;
import com.service.dao.ManagerDao;
import com.web.exceptions.ManagerDeleteException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class ManagerControllerTest extends DomainTestBase{
    
    @Autowired
    private ManagerDao managerDao;
    
    @Autowired
    private ManagerController controller;
    
    private Model model;
    
    @Before
    public void setUp(){
        model = new ExtendedModelMap();
    }

    @After
    public void cleanUp(){
        List<Manager> managers = managerDao.list();
        for(Manager manager : managers){
            managerDao.remove(manager);
        }
    }
    
    
    @Test
    public void testShowManagers(){
        Manager manager = new Manager("Eric");
        managerDao.add(manager);
        
        String view = controller.showManagers(model);
        assertEquals("managers/list", view);
        
        List<Manager> listFromDao = managerDao.list();
        Collection<?> listFromModel = (Collection<?>) model.asMap().get("managers");
        
        assertTrue(listFromModel.contains(manager));
        assertTrue(listFromDao.containsAll(listFromModel));
    }
    
   @Test 
   public void testDeleteManger() throws ManagerDeleteException{
       
       Manager manager = new Manager("Eric Shmidt");
       managerDao.add(manager);
       long id = manager.getId();
       
       String view = controller.deleteManager(id);
       
       assertEquals("redirect:/managers", view);
       assertNull(managerDao.find(id));
   }
   
   @Test(expected = ManagerDeleteException.class)
   public void testDeleteManagerThrowsExcwption() throws ManagerDeleteException{
       Manager manager = new Manager("Bill Gates");
       managerDao.add(manager);
       long id =  manager.getId();
       
       ManagerDao  mockedDao = mock(ManagerDao.class);
       when(mockedDao.removeManager(manager)).thenReturn(false);
         
       ManagerDao originalDao  = controller.getManagerDao();
       try{
           
           controller.setManagerDao(mockedDao);
           controller.deleteManager(id);
       }finally{
           controller.setManagerDao(originalDao);
       }
   }
   
   @Test
   public void testHandleDeleteException(){
       Manager manager = new Manager("Bill Gates");
       ManagerDeleteException e = new ManagerDeleteException(manager);
       ModelAndView modelAndView = controller.handleDeleteException(e);
       
       assertEquals("managers/delete-error", modelAndView.getViewName());
       assertTrue(modelAndView.getModelMap().containsValue(manager));
   }
   
   @Test
   public void testGetMAnager(){
       Manager manager = new Manager("Eric");
       managerDao.add(manager);
       Long id = manager.getId();
       
       String view = controller.getManager(id, model);
       assertEquals("managers/view", view);
       assertEquals(manager, model.asMap().get("manager"));
   }
   
   @Test
   public void testUpdateManager(){
       Manager manager  = new Manager("Bill");
       managerDao.add(manager);
       long id = manager.getId();
       
       manager.setName("Mark");
       
       String view = controller.updateManager(id, manager);
       assertEquals("redirect:/managers", view);
       assertEquals("Mark", managerDao.find(id).getName());
   }
   
   @Test
   public void testAddMAnager(){
       Manager manager  = new Manager("Bill Gates");
       
       String view = controller.addManager(manager);
       assertEquals("redirect:/managers", view);
       assertEquals(manager, managerDao.find(manager.getId()));
       
       
   }
}

