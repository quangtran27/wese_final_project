package com.onlinestorewepr.service;

import com.onlinestorewepr.dao.CartDAO;
import com.onlinestorewepr.dao.UserDAO;
import com.onlinestorewepr.entity.Cart;
import com.onlinestorewepr.entity.User;

public class CartService {
    public Cart getCart(int id) {
        CartDAO cartDAO = new CartDAO();
        Cart cart = cartDAO.get(id);
        return cart;
    }
}
