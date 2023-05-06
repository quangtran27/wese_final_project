package com.onlinestorewepr.service;

import com.onlinestorewepr.dao.CategoryDAO;
import com.onlinestorewepr.entity.Category;
import com.onlinestorewepr.util.MessageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryService {
  private HttpServletRequest req;
  private HttpServletResponse resp;
  private CategoryDAO categoryDAO;
  private MessageUtil message;

  public CategoryService(HttpServletRequest req, HttpServletResponse resp) {
    this.req = req;
    this.resp = resp;
    this.categoryDAO = new CategoryDAO();
    this.message = new MessageUtil();
  }

  public void ListCategories() throws ServletException, IOException {
    List<Category> categories = categoryDAO.getAll();
    // Sort
    if (categories != null) {
      categories.sort((o1, o2) -> {
        int compareValue = o1.getName().compareTo(o2.getName());
        return (Integer.compare(compareValue, 0));
      });
    }
    req.setAttribute("categories", categories);
    req.getRequestDispatcher("/admin/categories.jsp").forward(req, resp);
  }

  public void AddCategory() throws ServletException, IOException {
    String messageBody, messageType;
    String name = req.getParameter("name").trim();

    if (!name.isEmpty()) {
      // Check if a category with the same name already exists in DB
      if (categoryDAO.findByName(name) == null) {
        try {
          Category category = new Category(name);
          categoryDAO.insert(category);
          messageBody = "A new category was created successfully!";
          messageType = "success";
        } catch (Exception e) {
          e.printStackTrace();
          messageBody = "An error occurred when creating a new category! Please try again";
          messageType = "danger";
        }
      } else {
        messageBody = String.format("A category with name %s already exists! Please choose another name", name);
        messageType = "danger";
      }
    } else {
      messageBody = "Name and description cannot be empty!";
      messageType = "danger";
    }
    message.setBody(messageBody);
    message.setType(messageType);

    req.setAttribute("action", "add");
    req.setAttribute("message", message);
    req.getRequestDispatcher("/admin/update-category.jsp").forward(req, resp);
  }

  public void ShowUpdateCategory() throws ServletException, IOException {
    int id = req.getParameter("id") == null ? 0 : Integer.parseInt(req.getParameter("id"));
    Category category = categoryDAO.get(id);
    req.setAttribute("action", "update");
    req.setAttribute("category", category);
    req.setAttribute("message", message);
    req.getRequestDispatcher("/admin/update-category.jsp").forward(req, resp);
  }

  public void UpdateCategory() throws ServletException, IOException {
    String messageBody = "", messageType = "";
    int id = req.getParameter("id") == null ? 0 : Integer.parseInt(req.getParameter("id"));
    String name = req.getParameter("name").trim();

    if (id != 0 && !name.isEmpty()) {
      Category category = categoryDAO.get(id);
      if (category != null) {
        try {
          category.setName(name);
          categoryDAO.update(category);
          messageBody = "Category's info was changed successfully!";
          messageType = "success";
        } catch (Exception e) {
          e.printStackTrace();
          messageBody = "An error occurred when creating a new category! Please try again.";
          messageType = "danger";
        }
      } else {
        message.setBody("Category doesn't exist");
        message.setType("danger");
      }
    }
    else {
      messageBody = "All fields cannot be empty!";
      messageType = "danger";
    }
    message.setBody(messageBody);
    message.setType(messageType);

    req.setAttribute("message", message);
    req.getRequestDispatcher("/admin/update-category.jsp").forward(req, resp);
  }

  public void DeleteCategory() throws ServletException, IOException {
    String messageBody, messageType;
    int id = Integer.parseInt(req.getParameter("id"));
    if (id != 0) {
      Category category = categoryDAO.get(id);
      if (category != null) {
        if (category.getProducts().isEmpty()) {
          categoryDAO.delete(id);
          messageBody = "Category was deleted successfully!";
          messageType = "primary";
        } else {
          messageBody = "Cannot delete this category, this category has currently contains some products.";
          messageType = "danger";
        }
      }
      else {
        messageBody = "Category doesn't exist";
        messageType = "danger";
      }
    }
    else {
      messageBody = "Id doesn't exist";
      messageType = "danger";
    }
    message.setBody(messageBody);
    message.setType(messageType);

    req.setAttribute("message", message);
    req.setAttribute("title", "Delete Information");
    req.setAttribute("action", "/admin/category");
    req.getRequestDispatcher("/admin/information.jsp").forward(req, resp);
  }
}
