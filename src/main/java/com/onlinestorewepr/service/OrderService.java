package com.onlinestorewepr.service;

import com.onlinestorewepr.dao.CartDAO;
import com.onlinestorewepr.entity.Cart;
import com.onlinestorewepr.entity.Order;
import com.onlinestorewepr.entity.OrderItem;
import com.onlinestorewepr.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.onlinestorewepr.dao.OrderDAO;
import com.onlinestorewepr.dao.ProductDAO;
import com.onlinestorewepr.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
public class OrderService {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private OrderDAO orderDAO;
    private CartDAO cartDAO;

    private ProductDAO productDAO;
    public OrderService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.orderDAO = new OrderDAO();
        this.cartDAO = new CartDAO();
    }
    public void listAll() throws ServletException, IOException {
        List<Order> listOrder = OrderDAO.getAll();
        request.setAttribute("listOrder", listOrder);
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }

    public void editOrder() throws ServletException, IOException {
        productDAO = new ProductDAO();
        HttpSession session = request.getSession();
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        int idOrder = Integer.parseInt(request.getParameter("id"));
        Order order = orderDAO.get(idOrder);
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String status = request.getParameter("status");
        //String note = request.getParameter("note");
        order.setPhone(phone);
        order.setAddress(address);
        order.setStatus(status);
        //order.setNote(note);
        if(status.equals("Cancelled")){
            List getListOrderItem = orderDAO.getListOrderItem(idOrder);
            for(int i=0; i<getListOrderItem.size(); i++) {

                OrderItem row = (OrderItem) getListOrderItem.get(i);
                int quantity = row.getProduct().getQuantity()+ row.getQuantity();

                int productId = row.getProduct().getId();
                Product product = productDAO.get(productId);
                product.setQuantity(quantity);
                product.setSold(product.getSold() - 1);
                productDAO.update(product);
            }
        }
        orderDAO.update(order);
        if(status.equals("Cancelled")){
            response.sendRedirect("/admin/order");
        }
        else {
            //request.setAttribute("message", );
            String message = "Order's info was changed successfully!";
            response.sendRedirect("/admin/update-order?id=" + idOrder + "&message=success");
        }
        //request.getServletContext().getRequestDispatcher("/admin/list_order").forward(request, response);
    }
    public void update() throws  ServletException, IOException{
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();;
        int idOrder = Integer.parseInt(request.getParameter("id"));
        String message = request.getParameter("message");
        int total = 0;
        Order order = orderDAO.get(idOrder);
        List getListOrderItem = orderDAO.getListOrderItem(idOrder);
        for(int i=0; i<getListOrderItem.size(); i++) {
            OrderItem row = (OrderItem) getListOrderItem.get(i);
            total += row.getPrice() * row.getQuantity();
            orderItemList.add(row);
        }
        System.out.println(order.getStatus());
        System.out.println(order.getStatus());
        request.setAttribute("message", message);
        request.setAttribute("total", total);
        request.setAttribute("orderItemList", orderItemList);
        request.setAttribute("order", order);
    }
    public void updateOrderStatus(int id,String status) {
        OrderDAO orderDAO = new OrderDAO();
        Order order = orderDAO.get(id);
        String message = "", messageType = "";
        if (order != null) {
            try {
                if (!status.isEmpty())
                    order.setStatus(status);
                orderDAO.update(order);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            message = String.format("Order with id %s does not exist", id);
            messageType = "danger";
        }
    }
}