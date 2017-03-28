
package org.timesheet.web;

import com.web.controllers.DepartmentController;
import java.util.Collection;
import java.util.List;
import org.junit.After;
import org.junit.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.timesheet.DomainTestBase;
import com.domain.Department;
import com.service.dao.DepartmentDao;
import com.service.dao.EmployeeDao;
import com.web.exceptions.DepartmentDeleteException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class DepartmentControllerTest extends DomainTestBase{
    
    @Autowired
    DepartmentDao departmentDao;
    
    @Autowired
    EmployeeDao employeeDao;
    
    @Autowired
    DepartmentController controller;
    
  
    @After
    public void cleanUp(){
        List<Department> departments = departmentDao.list();
        for(Department department: departments){
            departmentDao.remove(department);
        }
    }
    
    @Test
    public void testSwowDepartmens(){
        Department department = new Department("tets-dep");
        departmentDao.add(department);
        
        ModelAndView mav = controller.showDepartments();
        String view = mav.getViewName();
        assertEquals("departments/list",view);
        
        List<Department> fromDao = departmentDao.list();
        
        Collection<?> listfromModel = (Collection<?>)mav.getModel().get("departments");
        
        assertTrue(listfromModel.contains(department));
        assertTrue(fromDao.containsAll(listfromModel));
        
    }
    
    @Test
    public void testDeleteDepartment()throws DepartmentDeleteException{
        Department department = new  Department("test-dep");
        departmentDao.add(department);
        long id = department.getId();
        
        String view = controller.deleteDepartment(id);
        
        assertEquals("redirect:/departments", view);
        assertNull(departmentDao.find(id));
    }
    
    @Test (expected = DepartmentDeleteException.class)
    public void testDeleteDepartmentThrowsException() throws DepartmentDeleteException{
        Department department = new Department("test-dep");
        departmentDao.add(department);
        long id =  department.getId();
        
        DepartmentDao mockedDao = mock(DepartmentDao.class);
        when(mockedDao.removeDepartment(department)).thenReturn(false);
        
       DepartmentDao originalDao = controller.getDepartmentDao();
       try{
           controller.setDepartmentDao(mockedDao);
           controller.deleteDepartment(id);
           
       }finally{
           controller.setDepartmentDao(originalDao);
       }
    }
    
    @Test
    public void tetsHandleDeleteException(){
        Department department = new Department("test-dep");
        DepartmentDeleteException e = new DepartmentDeleteException(department);
        ModelAndView modelAndView = controller.handleDeleteException(e);
        
        assertEquals("departments/delete-error", modelAndView.getViewName());
        assertTrue(modelAndView.getModelMap().containsValue(department));
    }
    
    @Test
    public void testGetDepartment(){
        Department dep = new Department("test-dep");
        departmentDao.add(dep);
        ModelAndView mav = controller.getDepartment(dep.getId());
        String view = mav.getViewName();
        assertEquals(view, "departments/view");
        assertEquals(dep, mav.getModel().get("department"));
    }
    
    @Test
    public void testUpdeteDepartment(){
        Department dep = new Department("test");
        departmentDao.add(dep);
        long id = dep.getId();
        
        dep.setName("updetaed");
        
        String view = controller.updateDepartment(id, dep);
        assertEquals("redirect:/departments", view);
        assertEquals("updetaed", departmentDao.find(id).getName());
        
    }
    
    @Test
    public void testAddDepartment(){
        Department dep = new Department("test");
        String view = controller.addDepartment(dep);
        assertEquals("redirect:/departments",view);
        assertEquals(dep, departmentDao.find(dep.getId()));
    }

}
