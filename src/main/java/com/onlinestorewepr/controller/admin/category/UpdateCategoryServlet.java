package com.onlinestorewepr.controller.admin.category;

import com.onlinestorewepr.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value={"/admin/category/update"})
public class UpdateCategoryServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    CategoryService categoryService = new CategoryService(req, resp);
    categoryService.ShowUpdateCategory();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    CategoryService categoryService = new CategoryService(req, resp);
    categoryService.UpdateCategory();
  }
}
