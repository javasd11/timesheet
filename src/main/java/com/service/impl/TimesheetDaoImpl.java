package com.service.impl;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import com.domain.Timesheet;
import com.service.dao.TimesheetDao;

import java.util.List;

@Repository("timesheetDao")
public class TimesheetDaoImpl extends HibernateDao<Timesheet, Long> implements TimesheetDao {

    @Override
    public List<Timesheet> list() {
        return currentSession().createCriteria(Timesheet.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}