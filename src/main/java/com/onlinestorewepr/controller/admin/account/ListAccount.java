package com.onlinestorewepr.controller.admin.account;

import com.onlinestorewepr.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/account")
public class ListAccount extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ListAccount(){super();}

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserService userService = new UserService(request, response);
        userService.listAll();
    }
}
