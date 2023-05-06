package com.onlinestorewepr.controller.web.user;

import com.onlinestorewepr.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class NewPassword
 */
@WebServlet("/web/newPassword")
public class NewPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserService userService = new UserService(request,response);
        response.setContentType("text/html;charset=UTF-8");
        userService.changePassForgot();
    }
}