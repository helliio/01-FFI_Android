package edu.ntnu.sair.dao.impl;

import edu.ntnu.sair.dao.LocationDao;
import edu.ntnu.sair.model.Location;
import edu.ntnu.sair.model.Member;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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

@Repository("locationDao")
public class LocationDaoImpl implements LocationDao {
    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Location location) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.save(location);
    }

    @Override
    public void delete(Location location) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.delete(location);
    }

    @Override
    public void update(Location location) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.update(location);
    }

    @Override
    public Location getById(long id) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from Location where id = " + id);
        if (q.list().size() == 0) {
            return null;
        }
        return (Location) q.list().get(0);
    }

    @Override
    public List<Location> getAll() {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from Location order by clienttimestamp desc");
        List<Location> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((Location) o);
        }
        return list;
    }

    @Override
    public List<Location> getByMember(Member member) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from Location where memberid = " + member.getId() + " order by clienttimestamp desc");
        List<Location> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((Location) o);
        }
        return list;
    }

    @Override
    public Location getByMemberLatest(Member member) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from Location where memberid = " + member.getId() + " order by clienttimestamp desc");
        if (q.list().size() == 0) {
            return null;
        }
        return (Location) q.list().get(0);
    }

    @Override
    public List<Location> getByTeam(String teamId) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from Location l" +
                " where l.member.teamId = '" + teamId + "'" +
                " order by l.clientTimestamp desc, l.member.id asc");
        List<Location> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((Location) o);
        }
        return list;
    }

    @Override
    public List<Location> getByTeamLatest(String teamId) {
        this.session = this.sessionFactory.getCurrentSession();
        Criteria q = this.session.createCriteria(Member.class);

        List<Location> list = new ArrayList<>();
        Iterator iterator = q.list().iterator();
        while (iterator.hasNext()) {
            q = this.session.createCriteria(Location.class)
                    .add(Restrictions.eq("member", iterator.next()))
                    .addOrder(Order.desc("clientTimestamp")).setMaxResults(1);
            if (q.list().size() > 0) {
                list.add((Location) q.list().get(0));
            }
        }
        return list;
    }

    @Override
    public List<Location> getByTeamPeriod(String teamId, long startTime, long endTime) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from Location l" +
                " where l.member.teamId = '" + teamId + "'" +
                " and l.clientTimestamp > :startTime and l.clientTimestamp < :endTime" +
                " order by l.clientTimestamp desc, l.member.id asc");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(startTime);
        calendar2.setTimeInMillis(endTime);
        q.setCalendar("startTime", calendar1);
        q.setCalendar("endTime", calendar2);
        List<Location> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((Location) o);
        }
        return list;
    }

}
