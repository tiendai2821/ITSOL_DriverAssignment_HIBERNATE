package com.dao;

import com.entity.Driver;
import com.hibernateutils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DriverDAO {
    public List<Driver> driverGetAll(){
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction tx=null;
        try {
            tx=session.beginTransaction();
            List<Driver> driverList=session.createQuery("from Driver ").list();
            session.getTransaction().commit();
            driverList.sort((Driver a, Driver b)-> a.getMaLx()-b.getMaLx());
            return driverList;
        }catch (HibernateException e){
            if(tx==null)
                tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
    public boolean saveDriver(Driver driver){
        Session session= HibernateUtil.getSessionFactory().openSession();
        Transaction tx=null;
        try {
            tx=session.beginTransaction();
            session.save(driver);
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
