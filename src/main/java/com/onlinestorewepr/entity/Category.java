package com.onlinestorewepr.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private String name;

   public Category() {
   }

   public Category(String name) {
      this.name = name;
   }

   @OneToMany(mappedBy = "category")
   private List<Product> products;

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<Product> getProducts() {
      return products;
   }

   public void setProducts(List<Product> products) {
      this.products = products;
   }
}
