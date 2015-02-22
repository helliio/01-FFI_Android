package edu.ntnu.sair.dao.impl;

import edu.ntnu.sair.dao.LocationDao;
import edu.ntnu.sair.model.Location;
import edu.ntnu.sair.model.Member;
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
        Query q = this.session.createQuery("from Location");
        List<Location> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((Location) o);
        }
        return list;
    }

    @Override
    public List<Location> getByMember(Member member) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from Location where memberid = " + member.getId());
        List<Location> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((Location) o);
        }
        return list;
    }

    @Override
    public List<Location> getByTeam(String teamId) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from Location l" +
                " where l.member.teamId = '" + teamId +"'" +
                " group by l.member.id, l.id order by l.clientTimestamp");
        List<Location> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((Location) o);
        }
        return list;
    }
}
