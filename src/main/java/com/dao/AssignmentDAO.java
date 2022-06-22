package com.dao;

import com.entity.Assignment;
import com.hibernateutils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AssignmentDAO {

    public List<Assignment> getAll() {
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction tx=null;
        try {
            tx=session.beginTransaction();
            List<Assignment> assignments=session.createQuery("from assignments ").list();
            session.getTransaction().commit();
            return assignments;
        }catch (HibernateException e){
            if(tx==null)
                tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    public boolean addNew(Assignment assignment) {
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction tx=null;
        try {
            tx=session.beginTransaction();
            session.save(assignment);
            session.getTransaction().commit();
            return true;
        }catch (HibernateException e){
            if(tx==null)
                tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;

    }

}
