package com.onlinestorewepr.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private String name;
   private String image="";
   @Column(columnDefinition = "LONGTEXT")
   private String description;
   private Double price;
   private int quantity;
   private int sold;
   private Double discount;
   private String size;
   private String color;
   private String brand;
   private boolean available=false;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "categoryId")
   private Category category;

   @Override
   public String toString() {
      return "Product{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", image='" + image + '\'' +
              ", description='" + description + '\'' +
              ", price=" + price +
              ", quantity=" + quantity +
              ", discount=" + discount +
              ", size='" + size + '\'' +
              ", color='" + color + '\'' +
              ", brand='" + brand + '\'' +
              ", category=" + category +
              ", cartItems=" + cartItems +
              ", orderItems=" + orderItems +
              '}';
   }

   @OneToMany(mappedBy = "product")
   private List<CartItem> cartItems;

   @OneToMany(mappedBy = "product")
   private List<OrderItem> orderItems;

   public Product(String name, String image, String description, Double price, int quantity, Double discount, String size, String color, String brand, boolean available, Category category) {
      this.name = name;
      this.image = image;
      this.description = description;
      this.price = price;
      this.quantity = quantity;
      this.sold = 0;
      this.discount = discount;
      this.size = size;
      this.color = color;
      this.brand = brand;
      this.available = available;
      this.category = category;
   }

   public Product() {

   }

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

   public String getImage() {
      return image;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Double getPrice() {
      return price;
   }

   public void setPrice(Double price) {
      this.price = price;
   }

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }

   public int getSold() {
      return sold;
   }

   public void setSold(int sold) {
      this.sold = sold;
   }

   public Double getDiscount() {
      return discount;
   }

   public void setDiscount(Double discount) {
      this.discount = discount;
   }

   public String getSize() {
      return size;
   }

   public void setSize(String size) {
      this.size = size;
   }

   public String getColor() {
      return color;
   }

   public void setColor(String color) {
      this.color = color;
   }

   public String getBrand() {
      return brand;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public boolean isAvailable() {
      return available;
   }

   public void setAvailable(boolean available) {
      this.available = available;
   }

   public Category getCategory() {
      return category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }

   public List<CartItem> getCartItems() {
      return cartItems;
   }

   public void setCartItems(List<CartItem> cartItems) {
      this.cartItems = cartItems;
   }

   public List<OrderItem> getOrderItems() {
      return orderItems;
   }

   public void setOrderItems(List<OrderItem> orderItems) {
      this.orderItems = orderItems;
   }

   // Validate data
   public boolean isPropertiesValid() {
      return (this.category != null &&
          !this.name.isEmpty() &&
          !this.image.isEmpty() &&
          !this.description.isEmpty() &&
          this.price > 0 &&
          this.discount >= 0 &&
          this.quantity >= 0 &&
          !this.size.isEmpty() &&
          !this.color.isEmpty() &&
          !this.brand.isEmpty());
   }
}
