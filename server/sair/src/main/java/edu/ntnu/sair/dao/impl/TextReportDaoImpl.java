package edu.ntnu.sair.dao.impl;

import edu.ntnu.sair.dao.TextReportDao;
import edu.ntnu.sair.model.Member;
import edu.ntnu.sair.model.TextReport;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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

    @Override
    public List<TextReport> getByMember(Member member) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from TextReport where memberid = " + member.getId() + " order by location.clienttimestamp desc");
        List<TextReport> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((TextReport) o);
        }
        return list;
    }

    @Override
    public TextReport getByMemberLatest(Member member) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from TextReport where memberid = " + member.getId() + " order by location.clienttimestamp desc");
        if (q.list().size() == 0) {
            return null;
        }
        return (TextReport) q.list().get(0);
    }

    @Override
    public List<TextReport> getByTeam(String teamId) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from TextReport o" +
                " where o.location.member.teamId = '" + teamId + "'" +
                " order by o.location.clientTimestamp desc, o.location.member.id asc");
        List<TextReport> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((TextReport) o);
        }
        return list;
    }

    @Override
    public List<TextReport> getByTeamLatest(String teamId) {
        this.session = this.sessionFactory.getCurrentSession();
        Criteria q = this.session.createCriteria(Member.class);

        List<TextReport> list = new ArrayList<>();
        Iterator iterator = q.list().iterator();
        while (iterator.hasNext()) {
            Query query = this.session.createQuery("from TextReport o" +
                    " where o.location.member.id = " + ((Member) iterator.next()).getId() +
                    " order by o.location.clientTimestamp desc ").setMaxResults(1);
            if (query.list().size() > 0) {
                list.add((TextReport) query.list().get(0));
            }
        }
        return list;
    }

    @Override
    public List<TextReport> getByTeamPeriod(String teamId, long startTime, long endTime) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from TextReport o" +
                " where o.location.member.teamId = '" + teamId + "'" +
                " and o.location.clientTimestamp > :startTime and o.location.clientTimestamp < :endTime" +
                " order by o.location.clientTimestamp desc, o.location.member.id asc");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(startTime);
        calendar2.setTimeInMillis(endTime);
        q.setCalendar("startTime", calendar1);
        q.setCalendar("endTime", calendar2);
        List<TextReport> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((TextReport) o);
        }
        return list;
    }
}
