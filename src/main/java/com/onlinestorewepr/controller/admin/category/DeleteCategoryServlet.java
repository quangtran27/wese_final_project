package com.onlinestorewepr.controller.admin.category;

import com.onlinestorewepr.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {"/admin/category/delete"})
public class DeleteCategoryServlet extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    CategoryService categoryBUS = new CategoryService(req, resp);
    categoryBUS.DeleteCategory();
  }
}
