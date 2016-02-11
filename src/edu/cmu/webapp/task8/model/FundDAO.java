package edu.cmu.webapp.task8.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.cmu.webapp.task8.databean.FundBean;

public class FundDAO {
    private SessionFactory sessionFactory;
    public FundDAO() {
        try {
            this.sessionFactory = DAOFactory.CreateSessionFactory();
        } catch (Exception e) {
//            System.out.println("cannot get session factory.");
            e.printStackTrace();
        }
    }
    public void createFund(FundBean fund) {
        if (fund == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            fund.setSymbol(fund.getSymbol().toUpperCase());
            session.save(fund);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot create a new fund into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateFund(FundBean fund) {
        if (fund == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(fund);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot update the fund into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public List<FundBean> getFundList() {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("from FundBean f");
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<FundBean> result = new ArrayList<FundBean>();
            while (it.hasNext()) {
                result.add((FundBean) it.next());
            }
            return result;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get the FundBean from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public FundBean getFundByName(String name) {
        if (name == null) {
            return null;
        }
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct f from FundBean f where f.name='%s'", name);
            session.beginTransaction();
            FundBean fund = (FundBean) session.createQuery(hql).uniqueResult();
            return fund;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get the FundBean from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public FundBean getFundBySymbol(String symbol) {
        if (symbol == null) {
            return null;
        }
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct f from FundBean f where f.symbol='%s'", symbol);
            session.beginTransaction();
            FundBean fund = (FundBean) session.createQuery(hql).uniqueResult();
            return fund;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get the FundBean from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public FundBean getFundById(int fundId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct f from FundBean f where f.fundId=%d", fundId);
            session.beginTransaction();
            FundBean fund = (FundBean) session.createQuery(hql).uniqueResult();
            return fund;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get the FundBean from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public List<FundBean> getCustomerFundList(int customerId) {
        Session session = this.sessionFactory.openSession();
        try {
        	String hql = String.format("SELECT DISTINCT fund FROM FundBean fund WHERE fundId IN (SELECT DISTINCT pos.fundId FROM PositionBean pos WHERE customerId = %d AND shares > 0)", customerId);
        	
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<FundBean> result = new ArrayList<FundBean>();
            while (it.hasNext()) {
                result.add((FundBean) it.next());
            }
            return result;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get fundPriceHistory by fund id from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public void deleteFund(FundBean fund) {
        if (fund == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(fund);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
//            System.out.println("cannot delete fund.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
