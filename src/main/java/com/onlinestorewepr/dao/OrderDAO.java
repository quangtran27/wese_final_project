package com.onlinestorewepr.dao;

import com.onlinestorewepr.entity.Order;
import com.onlinestorewepr.entity.User;
import com.onlinestorewepr.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class OrderDAO {
   public void insert(Order order) {
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         session.save(order);

         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }

   public void update(Order order) {
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         session.update(order);

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

         Order order = session.get(Order.class, id);
         if (order != null) {
            session.delete(order);
         }

         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }

   public static List<Order> getAll() {
      List<Order> orders = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         CriteriaBuilder builder = session.getCriteriaBuilder();
         CriteriaQuery<Order> criteriaQuery = builder.createQuery(Order.class);
         criteriaQuery.from(Order.class);
         orders = session.createQuery(criteriaQuery).getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return orders;
   }

   public Order get(int id) {
      Order order = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {

         order = session.get(Order.class, id);

      } catch (Exception e) {
         e.printStackTrace();
      }
      return order;
   }
   public List<Order> getOrderByUser(User user)
   {
      List<Order> orders = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         session.beginTransaction();
         CriteriaBuilder builder = session.getCriteriaBuilder();
         CriteriaQuery<Order> query = builder.createQuery(Order.class);
         Root<Order> root = query.from(Order.class);
         query.select(root); // SELECT
         query.where(builder.equal(root.get("user"), user));
         orders = session.createQuery(query).list();
         session.getTransaction().commit();

      } catch (Exception e) {
         e.printStackTrace();
      }
      return orders;
   }
   public List getListOrderItem(int orderId)
   {
      List results = null;
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();
         Query query = session.createQuery("FROM OrderItem o WHERE o.order.id = :orderId");
         query.setParameter("orderId", orderId);
         results = query.getResultList();
         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
      return results;
   }
//   public static void main(String[] args) {
//      User user = new User();
//      user.setUsername("dihuynh");
//      OrderDAO orderDAO = new OrderDAO();
//      List<Order> orders = orderDAO.getOrderByUser(user);
//      System.out.println(orders);
//   }
}
