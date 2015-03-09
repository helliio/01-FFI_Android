package edu.ntnu.sair.dao.impl;

import edu.ntnu.sair.dao.PhotoReportDao;
import edu.ntnu.sair.model.Member;
import edu.ntnu.sair.model.PhotoReport;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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

    @Override
    public List<PhotoReport> getByMember(Member member) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from PhotoReport where memberid = " + member.getId() + " order by location.clienttimestamp desc");
        List<PhotoReport> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((PhotoReport) o);
        }
        return list;
    }

    @Override
    public PhotoReport getByMemberLatest(Member member) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from PhotoReport where memberid = " + member.getId() + " order by location.clienttimestamp desc");
        if (q.list().size() == 0) {
            return null;
        }
        return (PhotoReport) q.list().get(0);
    }

    @Override
    public List<PhotoReport> getByTeam(String teamId) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from PhotoReport o" +
                " where o.location.member.teamId = '" + teamId + "'" +
                " order by o.location.clientTimestamp desc, o.location.member.id asc");
        List<PhotoReport> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((PhotoReport) o);
        }
        return list;
    }

    @Override
    public List<PhotoReport> getByTeamLatest(String teamId) {
        this.session = this.sessionFactory.getCurrentSession();
        Criteria q = this.session.createCriteria(Member.class);

        List<PhotoReport> list = new ArrayList<>();
        Iterator iterator = q.list().iterator();
        while (iterator.hasNext()) {
            Query query = this.session.createQuery("from PhotoReport o" +
                    " where o.location.member.id = " + ((Member) iterator.next()).getId() +
                    " order by o.location.clientTimestamp desc ").setMaxResults(1);
            if (query.list().size() > 0) {
                list.add((PhotoReport) query.list().get(0));
            }
        }
        return list;
    }

    @Override
    public List<PhotoReport> getByTeamPeriod(String teamId, long startTime, long endTime) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from PhotoReport o" +
                " where o.location.member.teamId = '" + teamId + "'" +
                " and o.location.clientTimestamp > :startTime and o.location.clientTimestamp < :endTime" +
                " order by o.location.clientTimestamp desc, o.location.member.id asc");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(startTime);
        calendar2.setTimeInMillis(endTime);
        q.setCalendar("startTime", calendar1);
        q.setCalendar("endTime", calendar2);
        List<PhotoReport> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((PhotoReport) o);
        }
        return list;
    }

}
