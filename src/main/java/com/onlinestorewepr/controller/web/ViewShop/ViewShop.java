package com.onlinestorewepr.controller.web.ViewShop;

import com.onlinestorewepr.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/shop")
public class ViewShop extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ProductService productService = new ProductService(req, resp);
    productService.viewProduct();
  }
}
