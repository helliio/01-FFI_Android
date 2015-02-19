package edu.ntnu.sair.dao.impl;

import edu.ntnu.sair.dao.MemberDao;
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

@Repository("memberDao")
public class MemberDaoImpl implements MemberDao {
    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(Member member) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.save(member);
    }

    @Override
    public void delete(Member member) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.delete(member);
    }

    @Override
    public void update(Member member) {
        this.session = this.sessionFactory.getCurrentSession();
        this.session.update(member);
    }

    @Override
    public Member getById(long id) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from Member where id = " + id);
        if (q.list().size() == 0) {
            return null;
        }
        return (Member) q.list().get(0);
    }

    @Override
    public List<Member> getAll() {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from Member");
        List<Member> list = new ArrayList<>();
        for (Object o : q.list()) {
            list.add((Member) o);
        }
        return list;
    }

    @Override
    public Member getByUsername(String username) {
        this.session = this.sessionFactory.getCurrentSession();
        Query q = this.session.createQuery("from Member where username = '" + username + "'");
        if (q.list().size() == 0) {
            return null;
        }
        return (Member) q.list().get(0);
    }
}
