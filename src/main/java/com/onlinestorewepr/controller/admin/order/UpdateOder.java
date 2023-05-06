package com.onlinestorewepr.controller.admin.order;

import com.onlinestorewepr.dao.OrderDAO;
import com.onlinestorewepr.dao.ProductDAO;
import com.onlinestorewepr.entity.Order;
import com.onlinestorewepr.entity.OrderItem;
import com.onlinestorewepr.entity.Product;
import com.onlinestorewepr.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/update-order")
public class UpdateOder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        OrderService orderService = new OrderService(request,response);
        orderService.update();
        request.getRequestDispatcher("update-order.jsp").forward(request, response);
    }
}
