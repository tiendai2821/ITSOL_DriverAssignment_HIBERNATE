package com.dao;

import com.entity.Route;
import com.hibernateutils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RouteDAO {
    public List<Route> getAllRoute(){
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction tx=null;
        try {
            tx=session.beginTransaction();
            List<Route> routeList=session.createQuery("from Route ").list();
            session.getTransaction().commit();
            routeList.sort((Route a,Route b) -> a.getMaTuyen()-b.getMaTuyen());
            return routeList;
        }catch (HibernateException e){
            if(tx==null)
                tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
    public boolean saveRoute(Route route){
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction tx=null;
        try {
            tx=session.beginTransaction();
            session.save(route);
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
