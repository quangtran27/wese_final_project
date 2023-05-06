package com.onlinestorewepr.controller.admin.account;

import com.onlinestorewepr.dao.OrderDAO;
import com.onlinestorewepr.dao.ProductDAO;
import com.onlinestorewepr.dao.UserDAO;
import com.onlinestorewepr.entity.Order;
import com.onlinestorewepr.entity.OrderItem;
import com.onlinestorewepr.entity.Product;
import com.onlinestorewepr.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/update-account")
public class UpdateAccount extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UpdateAccount() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService userService = new UserService(request,response);
        String username = request.getParameter("username");
        System.out.println(username);
        userService.getUser(username);
    }
}
