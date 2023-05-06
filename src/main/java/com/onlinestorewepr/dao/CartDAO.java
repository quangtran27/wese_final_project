package com.onlinestorewepr.dao;

import com.onlinestorewepr.entity.Cart;
import com.onlinestorewepr.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class CartDAO {
   public void insert(Cart cart) {
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         session.save(cart);

         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }

   public void update(Cart cart) {
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         session.update(cart);

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

         Cart cart = session.get(Cart.class, id);
         if (cart != null) {
            session.delete(cart);
         }
         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }

   public List<Cart> getAll() {
      List<Cart> carts = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         CriteriaBuilder builder = session.getCriteriaBuilder();
         CriteriaQuery<Cart> criteriaQuery = builder.createQuery(Cart.class);
         criteriaQuery.from(Cart.class);
         carts = session.createQuery(criteriaQuery).getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return carts;
   }

   public Cart get(int id) {
      Cart cart = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {

         cart = session.get(Cart.class, id);

      } catch (Exception e) {
         e.printStackTrace();
      }
      return cart;
   }
}
