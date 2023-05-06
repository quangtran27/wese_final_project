package com.onlinestorewepr.controller.admin.product;

import com.onlinestorewepr.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@WebServlet(value = {"/admin/product/update"})
public class UpdateProductServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ProductService productBUS = new ProductService(req, resp);
    productBUS.ShowUpdateProduct();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    ProductService productBUS = new ProductService(req, resp);
    productBUS.UpdateProduct();
  }
}
