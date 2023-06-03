package com.onlinestorewepr.controller.web.authentication;

import com.onlinestorewepr.dao.UserDAO;
import com.onlinestorewepr.service.UserService;
import com.onlinestorewepr.util.CSRFTokenGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = {"/login"})
public class UserLoginServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Cookie[] cookies = request.getCookies();
    if (cookies != null){
      for (Cookie i : cookies) {
        if (i.getName().equals("username")) {
          request.setAttribute("username",i.getValue());
          System.out.print(i.getValue());
        }
        if(i.getName().equals("password")){
          request.setAttribute("password",i.getValue());
          System.out.print(i.getValue());
        }
      }
    }
    if (request.getSession().getAttribute("userLogged") != null) {
      response.sendRedirect(request.getContextPath() + "/home");
    } else {

      String csrfToken = CSRFTokenGenerator.generateToken();
      request.getSession().setAttribute("csrfToken", csrfToken);

      request.getRequestDispatcher("/web/authentication.jsp").forward(request, response);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    UserService userServiceLogin = new UserService(req,resp);
    userServiceLogin.login();
  }
}
