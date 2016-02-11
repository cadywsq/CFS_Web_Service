package edu.cmu.webapp.task8.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.cmu.webapp.task8.databean.FundBean;
import edu.cmu.webapp.task8.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.databean.TransactionDetails;

public class TransactionDAO {
    private SessionFactory sessionFactory;
    public TransactionDAO() {
        try {
            this.sessionFactory = DAOFactory.CreateSessionFactory();
        } catch (Exception e) {
//            System.out.println("cannot get session factory.");
            e.printStackTrace();
        }
    }
    public void createTransaction(TransactionBean transaction) {
        if (transaction == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(transaction);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot create a new transaction into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateTransaction(TransactionBean transaction) {
        if (transaction == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(transaction);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot update the transaction into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void deleteTransaction(TransactionBean transaction) {
        if (transaction == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(transaction);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
//            System.out.println("cannot delete transaction.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public List<TransactionBean> findTransactionsByNullExecuteDate() {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct tx from TransactionBean tx where tx.executeDate is null");
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<TransactionBean> result = new ArrayList<TransactionBean>();
            while (it.hasNext()) {
                result.add((TransactionBean) it.next());
            }
            return result;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get TransactionBean by null execute date.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public List<TransactionBean> findTransactionsByCustomerId(int customerId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct tx from TransactionBean tx where tx.customerId=%d", customerId);
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<TransactionBean> result = new ArrayList<TransactionBean>();
            while (it.hasNext()) {
                result.add((TransactionBean) it.next());
            }
            return result;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get TransactionBean by fund id from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public List<TransactionDetails> getTransactionDetailsByCustomerId(int customerId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct tx from TransactionBean tx where tx.customerId=%d", customerId);
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<TransactionDetails> result = new ArrayList<TransactionDetails>();
            while (it.hasNext()) {
                TransactionBean transaction = (TransactionBean) it.next();
                TransactionDetails details = new TransactionDetails();
                details.setTransaction(transaction);
                if (transaction.getFundId() == -1) {
                    details.setFund(null);
                    details.setPriceHistory(null);
                } else {
                    String fundHql = String.format("select distinct f from FundBean f where f.fundId=%d", transaction.getFundId());
                    FundBean fund = (FundBean) session.createQuery(fundHql).uniqueResult();
                    details.setFund(fund);
                    String historyHql = String.format("from FundPriceHistoryBean fph where fph.fundId=%d and fph.priceDate='%s'", transaction.getFundId(), transaction.getExecuteDate());
                    FundPriceHistoryBean history = (FundPriceHistoryBean) session.createQuery(historyHql).uniqueResult();
                    details.setPriceHistory(history);
                }
                result.add(details);
            }
            return result;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            System.out.println("cannot get TransactionBean by fund id from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public List<TransactionBean> findTransactionsByFundId(int fundId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct tx from TransactionBean tx where tx.fundId=%d", fundId);
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            List<TransactionBean> result = new ArrayList<TransactionBean>();
            while (it.hasNext()) {
                result.add((TransactionBean) it.next());
            }
            return result;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get TransactionBean by fund id from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public int getLastTransactionId() {
    	Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select MAX(transactionId) from TransactionBean");
            session.beginTransaction();
            int lastTransactionId  = (int)session.createQuery(hql).uniqueResult();
            return lastTransactionId;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get Last Transactionid from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return 0;
    }
    public TransactionBean getLatestTransactionByCustomerId(int customerId) {
        Session session = this.sessionFactory.openSession();
        try {
            String hql = String.format("select distinct t from TransactionBean t where t.customerId=%d and t.executeDate=(select max(tt.executeDate) from TransactionBean tt where tt.customerId=%d and tt.executeDate is not null)", customerId, customerId);
            session.beginTransaction();
            List list = session.createQuery(hql).list();
            Iterator it = list.iterator();
            if (it.hasNext()) {
                TransactionBean tx = (TransactionBean) it.next();
                return tx;
            } else {
                return null;
            }
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get the latest transaction by customer id from database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public void deleteTransactionsByCustomerId(int customerId) {
        
    }
    public void deleteTransactionsByFundId(int fundId) {
        
    }
}
