package com.onlinestorewepr.dao;

import com.onlinestorewepr.entity.User;
import com.onlinestorewepr.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDAO {
   public void insert(User user) {
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         session.save(user);

         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }

   public void update(User user) {
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         session.update(user);

         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }

   public void delete(int id) {
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         User user = session.get(User.class, id);
         if (user != null) {
            session.delete(user);
         }

         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }

   public List<User> getAll() {
      List<User> users = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         CriteriaBuilder builder = session.getCriteriaBuilder();
         CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
         criteriaQuery.from(User.class);
         users = session.createQuery(criteriaQuery).getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return users;
   }

   public User get(String username) {
      User user = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         user = session.get(User.class, username);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return user;
   }
   public User findUserByEmail(String email) {
      User user = null;

      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         String HQL = "SELECT u FROM User u WHERE u.email = :email";
         Query query = session.createQuery(HQL);
         query.setParameter("email", email);
         List<User> users = query.getResultList();
         if (!users.isEmpty()) {
            user = users.get(0);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return user;
   }

   public User getAccount(String username, String password){
      User user = null;
      Transaction transaction =null;
      try (Session session=HibernateUtil.getSessionFactory().openSession()){
         transaction= session.beginTransaction();
         user= (User) session.get(User.class,username);

         transaction.commit();
      }
      catch (Exception e){
         e.printStackTrace();
      }
      return user;
   }

   public User findUserCreated(String username){
      User user = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         user = session.get(User.class, username);

      } catch (Exception e) {
         e.printStackTrace();
      }
      return user;
   }

//   public static void main(String[] args) {
//      UserDAO userDAO =new UserDAO();
//      User user = userDAO.findByEmail("phand613@gmail.com");
//      System.out.println(user);
//   }
}
