package com.onlinestorewepr.controller.admin.order;

import com.onlinestorewepr.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/admin/order")
public class ListOderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ListOderServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderService orderServices = new OrderService(request, response);
        orderServices.listAll();
    }
}