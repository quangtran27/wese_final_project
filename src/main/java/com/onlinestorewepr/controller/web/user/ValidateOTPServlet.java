package com.onlinestorewepr.controller.web.user;

import com.onlinestorewepr.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/web/ValidateOtp")
public class ValidateOTPServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        UserService userService = new UserService(request,response);
        userService.validateOTP();
    }
}
