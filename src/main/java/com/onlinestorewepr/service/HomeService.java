package com.onlinestorewepr.service;

import com.onlinestorewepr.dao.ProductDAO;
import com.onlinestorewepr.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class HomeService {
  HttpServletRequest req;
  HttpServletResponse resp;

  public HomeService(HttpServletRequest req, HttpServletResponse resp) {
    this.resp = resp;
    this.req = req;
  }

  public void ShowHomePage() throws ServletException, IOException {
    ProductDAO productDAO = new ProductDAO();
    List<Product> bestsellers = productDAO.getBestsellersProducts();
    List<Product> newArrivals = productDAO.getNewArrivals();

    req.setAttribute("bestsellers", bestsellers);
    req.setAttribute("newArrivals", newArrivals);
    req.getRequestDispatcher("/web/index.jsp").forward(req, resp);
  }
}
