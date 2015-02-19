package edu.ntnu.sair.dao.impl;

import edu.ntnu.sair.dao.PhotoReportDao;
import edu.ntnu.sair.model.PhotoReport;
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

@Repository("photoReportDao")
public class PhotoReportDaoImpl implements PhotoReportDao {
    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(PhotoReport photoReport) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.save(photoReport);
    }

    @Override
    public void delete(PhotoReport photoReport) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.delete(photoReport);
    }

    @Override
    public void update(PhotoReport photoReport) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.update(photoReport);
    }

    @Override
    public PhotoReport getById(long id) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from PhotoReport where id = " + id);
        if (q.list().size() == 0) {
            return null;
        }
        return (PhotoReport) q.list().get(0);
    }

    @Override
    public List<PhotoReport> getAll() {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from PhotoReport");
        List<PhotoReport> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((PhotoReport) o);
        }
        return list;
    }
}
