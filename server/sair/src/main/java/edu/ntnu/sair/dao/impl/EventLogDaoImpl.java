package edu.ntnu.sair.dao.impl;

import edu.ntnu.sair.dao.EventLogDao;
import edu.ntnu.sair.model.EventLog;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chun on 2/10/15.
 */

@Repository("eventLogDao")
public class EventLogDaoImpl implements EventLogDao {
    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(EventLog eventLog) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.save(eventLog);
    }

    @Override
    public void delete(EventLog eventLog) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.delete(eventLog);
    }

    @Override
    public void update(EventLog eventLog) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.update(eventLog);
    }

    @Override
    public EventLog getById(long id) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createSQLQuery("from Member where id = " + id);
        if (q.list().size() == 0) {
            return null;
        }
        return (EventLog) q.list().get(0);
    }

    @Override
    public List<EventLog> getAll() {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createSQLQuery("from EventLog");
        List<EventLog> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((EventLog) o);
        }
        return list;
    }
}
