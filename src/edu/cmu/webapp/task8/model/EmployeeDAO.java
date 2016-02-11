package edu.cmu.webapp.task8.model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import edu.cmu.webapp.task8.databean.EmployeeBean;

public class EmployeeDAO {
    private SessionFactory sessionFactory;
    public EmployeeDAO() {
        try {
            this.sessionFactory = DAOFactory.CreateSessionFactory();
        } catch (Exception e) {
//            System.out.println("cannot get session factory.");
            e.printStackTrace();
        }
    }
    public void createEmployee(EmployeeBean employee) {
        if (employee == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot create a new employee into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void updateEmployee(EmployeeBean employee) {
        if (employee == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
//            System.out.println("cannot update the employee into database.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public EmployeeBean getEmployeeByUserName(String username) {
        if (username == null || username.length() == 0) {
            return null;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            EmployeeBean employee = (EmployeeBean) session.get(EmployeeBean.class, username);
            session.getTransaction().commit();
            return employee;
        } catch (Exception e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get employee by username.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
    public void deleteEmployeeById(int username) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            EmployeeBean employee = (EmployeeBean) session.get(EmployeeBean.class, username);
            session.delete(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
//            System.out.println("cannot get employee by username.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    public void deleteEmployee(EmployeeBean employee) {
        if (employee == null) {
            return;
        }
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(employee);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
//            System.out.println("cannot delete employee.");
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
