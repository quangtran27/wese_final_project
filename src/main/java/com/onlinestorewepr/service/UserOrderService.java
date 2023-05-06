package com.onlinestorewepr.service;

import com.onlinestorewepr.dao.*;
import com.onlinestorewepr.entity.*;
import com.onlinestorewepr.util.EmailUtil;
import com.onlinestorewepr.util.MessageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserOrderService {
  private HttpServletRequest req;
  private HttpServletResponse resp;
  private OrderDAO orderDAO;
  private MessageUtil messageUtil;

  public HttpServletRequest getReq() {
    return req;
  }
  public void setReq(HttpServletRequest req) {
    this.req = req;
  }
  public HttpServletResponse getResp() {
    return resp;
  }
  public void setResp(HttpServletResponse resp) {
    this.resp = resp;
  }
  public OrderDAO getOrderDAO() {
    return orderDAO;
  }
  public void setOrderDAO(OrderDAO orderDAO) {
    this.orderDAO = orderDAO;
  }
  public MessageUtil getMessageUtil() {
    return messageUtil;
  }
  public void setMessageUtil(MessageUtil messageUtil) {
    this.messageUtil = messageUtil;
  }
  public UserOrderService(HttpServletRequest req, HttpServletResponse resp) {
    this.req = req;
    this.resp = resp;
    this.orderDAO = new OrderDAO();
    this.messageUtil = new MessageUtil();
  }

  public void viewCheckout() throws IOException {
    // Fake user login
    HttpSession session = req.getSession();
    User user = (User)session.getAttribute("userLogged");
    if (user != null) {
      try {
        Cart cart = user.getCart();
        // Get selected cart item
        String[] selectedCartItemIds = req.getParameter("cart_items").split(",");
        List<CartItem> cartItems = new ArrayList<>();
        for (String item: selectedCartItemIds) {
          int cartItemId = Integer.parseInt(item);
          cartItems.add(new CartItemDAO().get(cartItemId));
        }

        req.setAttribute("cartItems", cartItems);
        req.setAttribute("user", user);
        req.getRequestDispatcher("/web/checkout.jsp").forward(req, resp);

      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    else {
      resp.sendRedirect("/login");
    }
  }

  public void addOrder() throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    String subject;
    String body;
    String action;
    String actionTitle;

    // Fake user login
    HttpSession session = req.getSession(true);
    User user = (User)session.getAttribute("userLogged");
    if (user != null) {
      try {
        // Get data from request
        String fullname = req.getParameter("name");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String email = req.getParameter("email");
        String note = req.getParameter("note");
        String[] cartItemIds = req.getParameterValues("cartItem");
        double total = Double.parseDouble(req.getParameter("total"));
        String payment = req.getParameter("payment-mode");

        boolean isValidData = !fullname.isEmpty() &&
                              !phone.isEmpty() &&
                              !address.isEmpty() &&
                              !email.isEmpty() &&
                              cartItemIds.length > 0 &&
                              total >= 0 &&
                              !payment.isEmpty();

        if (isValidData) {
          List<OrderItem> orderItems = new ArrayList<>();
          for (String idString: cartItemIds) {
            int cartItemId = Integer.parseInt(idString);
            CartItem cartItem = new CartItemDAO().get(cartItemId);
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setPrice(cartItem.getProduct().getDiscount() == 0 ? cartItem.getProduct().getPrice() : cartItem.getProduct().getDiscount());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
          }

          Order order = new Order(fullname, phone, address, new Date(), total, note, payment, "created", user, orderItems);
          orderDAO.insert(order);

          // Delete the corresponding cartItem
          CartItemDAO cartItemDAO = new CartItemDAO();
          for (String idString: cartItemIds) {
            int cartItemId = Integer.parseInt(idString);
            cartItemDAO.delete(cartItemId);
          }

          // Reduce the number of product
          ProductDAO productDAO = new ProductDAO();
          OrderItemDAO orderItemDAO = new OrderItemDAO();
          for (OrderItem orderItem: orderItems) {
            orderItem.setOrder(order);
            orderItemDAO.update(orderItem);
            Product product = orderItem.getProduct();
            product.setQuantity(product.getQuantity() - orderItem.getQuantity());
            product.setSold(product.getSold() + 1);
            productDAO.update(product);
          }

          // => success
          if (payment.equals("cod")) {
            body = "Thank you for ordering!<br> Your order has been received and will be delivered within a few days.";
          } else {
            body = "Thank you for ordering!<br> Your order has been received.  <br>Our staff will contact you to confirm payment!";
          }
          action = "/order";
          actionTitle = "Go to order management page";

          // Send email notify
          String emailSubject = "New Order (#" + order.getId() + ") from Male Fashion Store for & " + order.getTotal();
          StringBuilder emailBody = new StringBuilder("*** This is an automated message - please do not reply directly to this email ***\n");
          emailBody.append("Customer: ").append(fullname).append("\n");
          emailBody.append("Phone: ").append(phone).append("\n");
          emailBody.append("Address: ").append(address).append("\n");
          emailBody.append("Your order details:");

          for (OrderItem orderItem : orderItems) {
            String data = "\n - " + orderItem.getProduct().getName() + " " + orderItem.getProduct().getColor() + " " + orderItem.getProduct().getSize() + " x " + orderItem.getQuantity();
            emailBody.append(data);
          }
          emailBody.append("\nThank you for ordering!");

          EmailUtil.sendEmail(email, emailSubject, emailBody.toString(), "tranvanquangforever@gmail.com", "fmhwuvwkglyggykn");
        } else {
          body = "Input data is invalid! Please try again";
          action = "/cart";
          actionTitle = "Return to cart page";
        }
        subject = "Notification";
        req.setAttribute("subject", subject);
      } catch (Exception ex) {
        body = ex.getMessage();
        action = "/cart";
        actionTitle = "Return to cart page";
      }

      req.setAttribute("body", body);
      req.setAttribute("action", action);
      req.setAttribute("actionTitle", actionTitle);
      req.getRequestDispatcher("/web/information.jsp").forward(req, resp);
    }
  }
}
