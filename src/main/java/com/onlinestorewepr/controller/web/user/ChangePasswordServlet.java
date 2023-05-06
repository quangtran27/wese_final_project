package com.onlinestorewepr.controller.web.user;

import com.onlinestorewepr.dao.UserDAO;
import com.onlinestorewepr.entity.User;
import com.onlinestorewepr.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ChangPasswordUserServlet" ,value = {"/change-password","/web/change-password"})
public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User userLogged = (User)req.getSession().getAttribute("userLogged");
        User user = new UserDAO().get(userLogged.getUsername());
        req.setAttribute("user", user);
        req.getRequestDispatcher("/web/change_pass.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        UserService userService = new UserService(req,resp);
        userService.changeUserPassword();
    }
}
