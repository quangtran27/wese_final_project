package com.onlinestorewepr.controller.admin.category;

import com.onlinestorewepr.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {"/admin/category/add"})
public class AddCategoryServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setAttribute("action", "add");
    req.getRequestDispatcher("/admin/update-category.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    CategoryService categoryBUS = new CategoryService(req, resp);
    categoryBUS.AddCategory();
  }
}
