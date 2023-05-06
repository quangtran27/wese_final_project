package com.onlinestorewepr.dao;

import com.onlinestorewepr.entity.Category;
import com.onlinestorewepr.entity.Product;
import com.onlinestorewepr.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

public class ProductDAO {
   public void insert(Product product) {
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         session.save(product);

         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }

   public void update(Product product) {
      Transaction transaction = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         transaction = session.beginTransaction();

         session.update(product);

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

         Product product = session.get(Product.class, id);
         if (product != null) {
            session.delete(product);
         }

         transaction.commit();
      } catch (Exception e) {
         e.printStackTrace();
         if (transaction != null) {
            transaction.rollback();
         }
      }
   }

   public List<Product> getAll() {
      List<Product> products = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         CriteriaBuilder builder = session.getCriteriaBuilder();
         CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
         criteriaQuery.from(Product.class);
         products = session.createQuery(criteriaQuery).getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return products;
   }

   public Product get(int id) {
      Product product = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         product = session.get(Product.class, id);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return product;
   }

   public Product findByName(String name) {
      Product product = null;

      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         String HQL = "SELECT c FROM Product c WHERE c.name = :name";
         Query query = session.createQuery(HQL);
         query.setParameter("name", name);
         List<Product> products = query.getResultList();
         if (!products.isEmpty()) {
            product = products.get(0);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      return product;
   }
//Lấy tất cả sản phẩm có cùng ID category
    public List<Product> getTopbyCategory(int CategoryID) {
      List<Product> products = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         String HQL = "SELECT c FROM Product c WHERE category.id = :CategoryID";
         Query query = session.createQuery(HQL);
         query.setParameter("CategoryID", CategoryID);
         products = query.setMaxResults(4).getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return products;
   }

   public List<Product> filterProduct(int CategoryID, String brand,double price, String size, int sortPrice) {
      List<Product> products = null;

      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         String HQL = "FROM  Product c where c.name!=null";
         Query query;

         if(CategoryID != 0){
            HQL = HQL + " and category.id = :CategoryID";
         }
         if (brand !=null) {
            HQL = HQL + " and c.brand = :brand";
         }
         if (size != null) {
            HQL = HQL + " and c.size = :size";
         }

         if (price != 0) {
            if (price == 1){
               HQL = HQL + " and c.price <= 100";
            }
            else if(price == 2){
               HQL = HQL + " and c.price >= 100 and c.price <= 200";
            }
            else {
               HQL = HQL + " and c.price >= 200";
            }
         }

         if (sortPrice != 0) {
            if (sortPrice == 1){
               HQL = HQL + " ORDER BY c.price";
            }
            else{
               HQL = HQL + " ORDER BY c.price desc";
            }
         }

         query = session.createQuery(HQL);
         if(CategoryID != 0){
            query.setParameter("CategoryID", CategoryID);
         }
         if (brand !=null) {
            query.setParameter("brand", brand);
         }
         if (size != null) {
            query.setParameter("size", size);
         }

         products = query.getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return products;
   }
/*lấy các brand*/
   public List<String> getBrand() {
      List<Product> products = null;
      List<String> brand = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         String HQL = "select distinct c.brand from Product c";
         Query query = session.createQuery(HQL);
         brand = query.getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return brand;
   }
   /*lấy các size*/
   public List<String> getSize() {
      List<String> size = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         String HQL = "select distinct c.size from Product c";
         Query query = session.createQuery(HQL);
         size = query.getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return size;
   }

   /*get color*/
   public List<String> getColor() {
      List<String> color = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         String HQL = "select distinct c.color from Product c";
         Query query = session.createQuery(HQL);
         color = query.getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return color;
   }

   public List<Product> getNewArrivals() {
      List<Product> products = new ArrayList<>();
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         String HQL = "FROM  Product c order by c.id desc";
         Query query = session.createQuery(HQL).setMaxResults(8);
         products = query.getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return products;
   }
   /*Lọc sản phẩm theo tên*/
   public List<Product> searchByName(String txtSearch) {
      List<Product> products = null;
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         String HQL = "SELECT c FROM Product c WHERE c.name LIKE :txtSearch";
         Query query = session.createQuery(HQL);
         query.setParameter("txtSearch", "%"+txtSearch+"%");
         products = query.getResultList();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return products;
   }
/*phaan trang*/
   public List<Product> getListByPage(List<Product> list, int start, int end) {
      ArrayList<Product> products = new ArrayList<>();
      for(int i = start;i<end;i++){
         products.add(list.get(i));
      }
      return products;
   }

    public static void main(String[] args) {
     ProductDAO donHangDAO = new ProductDAO();

     List<Product> products = donHangDAO.filterProduct(0,"",0,"",1);
     System.out.println(products.size());
     for (Product p : products) {
        System.out.println(p.getPrice());
     }
  }
   public Product getProductsByOrderItem(Product pr) {
      Product product = new Product();
      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
         session.beginTransaction();
         CriteriaBuilder builder = session.getCriteriaBuilder();
         CriteriaQuery<Product> query = builder.createQuery(Product.class);
         Root<Product> root = query.from(Product.class); // FROM User u
         query.select(root); // SELECT
         query.where(builder.equal(root.get("id"), pr.getId())); // WHERE u.id = 1
         product = session.createQuery(query).uniqueResult();

         session.getTransaction().commit();

      } catch (Exception e) {
         e.printStackTrace();
      }
      return product;
   }
   public List<Product> getBestsellersProducts() {
         List<Product> products = new ArrayList<>();
         try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String HQL = "SELECT p FROM Product p ORDER BY p.sold DESC";
            Query query = session.createQuery(HQL).setMaxResults(8);
            products = query.getResultList();
         } catch (Exception e) {
            e.printStackTrace();
         }
         return products;
      }
}
