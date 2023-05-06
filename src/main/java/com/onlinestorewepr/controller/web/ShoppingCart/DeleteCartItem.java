package com.onlinestorewepr.controller.web.ShoppingCart;

import com.onlinestorewepr.service.CartItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart/delete")
public class DeleteCartItem extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //String username = (String) request.getSession().getAttribute("username");
        CartItemService cartItemService = new CartItemService(request, response);
        cartItemService.delete();
    }
}
