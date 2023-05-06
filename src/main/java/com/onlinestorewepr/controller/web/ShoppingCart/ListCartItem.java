package com.onlinestorewepr.controller.web.ShoppingCart;

import com.onlinestorewepr.entity.CartItem;
import com.onlinestorewepr.service.CartItemService;
import com.onlinestorewepr.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class ListCartItem extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //String username = (String) request.getSession().getAttribute("username");

        CartItemService cartItemService = new CartItemService(request, response);
        cartItemService.ListCartItem();
    }
}
