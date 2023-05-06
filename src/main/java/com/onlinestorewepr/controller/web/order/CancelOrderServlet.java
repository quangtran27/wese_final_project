package com.onlinestorewepr.controller.web.order;

import com.onlinestorewepr.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cancelOrder")
public class CancelOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("orderId"));
        OrderService orderService = new OrderService(request,response);
        orderService.updateOrderStatus(id,"Cancelled");
        request.getRequestDispatcher("/web/profile").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
