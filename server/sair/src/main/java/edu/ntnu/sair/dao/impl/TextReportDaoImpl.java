package edu.ntnu.sair.dao.impl;

import edu.ntnu.sair.dao.TextReportDao;
import edu.ntnu.sair.model.TextReport;
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

@Repository("textReportDao")
public class TextReportDaoImpl implements TextReportDao {
    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(TextReport textReport) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.save(textReport);
    }

    @Override
    public void delete(TextReport textReport) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.delete(textReport);
    }

    @Override
    public void update(TextReport textReport) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.update(textReport);
    }

    @Override
    public TextReport getById(long id) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from TextReport where id = " + id);
        if (q.list().size() == 0) {
            return null;
        }
        return (TextReport) q.list().get(0);
    }

    @Override
    public List<TextReport> getAll() {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from TextReport");
        List<TextReport> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((TextReport) o);
        }
        return list;
    }
}
