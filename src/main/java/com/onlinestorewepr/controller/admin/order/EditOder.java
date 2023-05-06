package com.onlinestorewepr.controller.admin.order;

import com.onlinestorewepr.dao.OrderDAO;
import com.onlinestorewepr.entity.Order;
import com.onlinestorewepr.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/EditOder")
public class EditOder extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderService orderService = new OrderService(request, response);
        orderService.editOrder();
    }
}
