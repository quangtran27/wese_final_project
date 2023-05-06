package com.onlinestorewepr.controller.admin.product;

import com.onlinestorewepr.entity.Category;
import com.onlinestorewepr.service.CategoryService;
import com.onlinestorewepr.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet(value = {"/admin/product/add", "/admin/product/add/"})
public class AddProductServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ProductService productBUS = new ProductService(req, resp);
    productBUS.ShowAddProduct();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    ProductService productBUS = new ProductService(req, resp);
    productBUS.AddProduct();
  }
}
