package com.onlinestorewepr.controller.web.order;

import com.onlinestorewepr.service.UserOrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddOrderServlet", value = {"/order/add"})
public class AddOrderServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    UserOrderService userOrderService = new UserOrderService(req, resp);
    userOrderService.addOrder();
  }
}
