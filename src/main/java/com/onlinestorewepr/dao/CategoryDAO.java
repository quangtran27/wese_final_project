package com.onlinestorewepr.dao;

import com.onlinestorewepr.entity.Category;
import com.onlinestorewepr.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class CategoryDAO {
   public void insert(Category category) {
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         session.save(category);

         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }

   public void update(Category category) {
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         session.update(category);

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

         Category category = session.get(Category.class, id);
         if (category != null) {
            session.delete(category);
         }

         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }

   public List<Category> getAll() {
      List<Category> categories = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         CriteriaBuilder builder = session.getCriteriaBuilder();
         CriteriaQuery<Category> criteriaQuery = builder.createQuery(Category.class);
         criteriaQuery.from(Category.class);
         categories = session.createQuery(criteriaQuery).getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return categories;
   }

   public Category get(int id) {
      Category category = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {

         category = session.get(Category.class, id);

      } catch (Exception e) {
         e.printStackTrace();
      }
      return category;
   }


   public Category findByName(String name) {
      Category category = null;

      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         String HQL = "SELECT c FROM Category c WHERE c.name = :name";
         Query query = session.createQuery(HQL);
         query.setParameter("name", name);
         List<Category> categories = query.getResultList();
         if (!categories.isEmpty()) {
            category = categories.get(0);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      return category;
   }
}
