package com.onlinestorewepr.service;

import com.onlinestorewepr.dao.*;
import com.onlinestorewepr.entity.Cart;
import com.onlinestorewepr.entity.CartItem;
import com.onlinestorewepr.entity.Product;
import com.onlinestorewepr.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartItemService {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    CartItemDAO cartItemDAO;
    public List<CartItem> getListCartItem(int id) {
        return cartItemDAO.getList(id);
    }
    public CartItemService(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
        cartItemDAO = new CartItemDAO();
    }

    public void addProduct() throws IOException {
        User loggedUser = (User) req.getSession().getAttribute("userLogged");
        if (loggedUser != null) {
            User user = new UserDAO().get(loggedUser.getUsername());
            ProductService productService = new ProductService(req, resp);
            CartItemDAO cartItemdao = new CartItemDAO();

            int productID =Integer.parseInt(req.getParameter("ProductId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            Product product = productService.getProduct(productID);
            Cart cart = user.getCart();

            if (quantity > 0 && quantity <= product.getQuantity()) {
                // Cart had product(s)
                if (cart.getCartItems().size() > 0) {
                    boolean isExisted = false;
                    for (CartItem c : cart.getCartItems()) {
                        // Cart already has this product
                        if (c.getProduct().getId() == productID) {
                            c.setQuantity(c.getQuantity() + quantity);
                            cartItemDAO.update(c);
                            isExisted = true;
                            break;
                        }
                    }
                    if (!isExisted) {
                        CartItem cartItem = new CartItem(cart, product, quantity);
                        cartItemdao.insert(cartItem);
                    }
                }
                // Cart is empty
                else {
                    CartItem cartItem = new CartItem(cart, product, quantity);
                    cartItemdao.insert(cartItem);
                }
                resp.sendRedirect(req.getContextPath() + "/shop?message=The%20product%20has%20been%20successfully%20added%20to%20your%20cart&messageType=success");
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/shop?message=An%20error%20occurred,%20the%20product%20has%20not%20been%20added%20to%20your%20cart&messageType=danger");
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

    public void ListCartItem () throws IOException, ServletException {
        User loggedUser = (User)req.getSession().getAttribute("userLogged");
        if (loggedUser != null) {
            User user = new UserDAO().get(loggedUser.getUsername());
            Cart cart = user.getCart();

            List<CartItem> cartItems = cart.getCartItems();
            String message = req.getParameter("message");
            if (message != null){
                req.setAttribute("message", message);
            }
            req.setAttribute("cartItems", cartItems);
            req.getRequestDispatcher("/web/shopping-cart.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
    public void delete() throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        CartItemDAO cartItemDAO =new CartItemDAO();
        cartItemDAO.delete(id);
        String message = "Delete successfully";
        resp.sendRedirect("/cart?message=" + message);
    }

    public void updateCartItem() throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String action = req.getParameter("action");
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        CartItem cartItem = cartItemDAO.get(id);
        String message = null;
        int quantityProduct = cartItem.getProduct().getQuantity();
        System.out.println("Action: " + action);
        if (action.equals("inc") && quantity == quantityProduct){
            message = "Out of stock";
        }
        else if (action.equals("dec") && quantity == 0){
            message = "Can't reduce the quantity";
        } else if (action.equals("inc")) {
            quantity = quantity + 1;
            System.out.println(quantity);
            cartItem.setQuantity(quantity);
            cartItemDAO.update(cartItem);
            System.out.println(cartItem.getQuantity());
        } else if (action.equals("dec")) {
            quantity = quantity - 1;
            cartItem.setQuantity(quantity);
            System.out.println(quantity);
            cartItemDAO.update(cartItem);
        }
        if (message!= null) {
            resp.sendRedirect("/cart?message="+message);
        }
        else {
            resp.sendRedirect("/cart");
        }
    }
}
