package edu.ntnu.sair.dao.impl;

import edu.ntnu.sair.dao.MemberDao;
import edu.ntnu.sair.model.Member;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("memberDao")
public class MemberDaoImpl implements MemberDao{
    private SessionFactory sessionFactory;
    private Session session;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Autowired
    public void setSession(Session session) {
        this.session = session;
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
        Query q = this.session.createSQLQuery("from Member where id = " +id);
        if(q.list().size() == 0){
            return null;
        }
        return (Member)q.list().get(0);
    }

    @Override
    public List<Member> getAll() {
        return null;
    }
}
