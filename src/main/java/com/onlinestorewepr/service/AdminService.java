package com.onlinestorewepr.service;

import com.onlinestorewepr.dao.OrderDAO;
import com.onlinestorewepr.dao.OrderItemDAO;
import com.onlinestorewepr.dao.ProductDAO;
import com.onlinestorewepr.dao.UserDAO;
import com.onlinestorewepr.entity.User;
import com.onlinestorewepr.util.CommonUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AdminService {
    private UserDAO userDAO;
    private ServiceResult serviceResult;

    HttpServletResponse resp;
    HttpServletRequest req;

    public AdminService(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
        userDAO = new UserDAO();
        serviceResult = new ServiceResult();
    }

    public User authenticate(String username, String password) {
        User user = userDAO.get(username);
        if (user != null && user.isAdmin()) {
            if (password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public void adminLogin() throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = authenticate(username, password);
        User userCreated = userDAO.get(username);
        String errMessage = "";
        boolean hasError = false;

        if (username == null || password == null || username.length() == 0 || password.length() == 0) {
            hasError = true;
            errMessage = "Username & Password cannot be empty!";
        } else {
            try {
                if (user == null) {
                    hasError = true;
                    errMessage = "Username or Password is incorrect!";
                }
                if (userCreated == null) {
                    hasError = true;
                    errMessage = "Account does not exist!";
                }
            } catch (Exception e) {
                e.printStackTrace();
                hasError = true;
                errMessage = e.getMessage();
            }
        }

        if (hasError) {
            req.setAttribute("message", errMessage);
            req.getRequestDispatcher("/admin/login.jsp").forward(req, resp);
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("adminLogged", user);
            session.setMaxInactiveInterval(1000);
            resp.sendRedirect("/admin/index.jsp");
        }
    }
    public void showProfile() throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        User adminLogged = (User)req.getSession().getAttribute("adminLogged");
        User user = new UserDAO().get(adminLogged.getUsername());
        req.setAttribute("user", user);
        req.getRequestDispatcher("/admin/account-profile.jsp").forward(req,resp);
    }
    public void updateAdminProfile() throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        User user = (User) req.getSession().getAttribute("adminLogged");
        String fullName = req.getParameter("name");
        String phone = req.getParameter("phone");
        String gender = req.getParameter("sex");
        String address = req.getParameter("address");
        Part part = req.getPart("image");

        //Update image
        if (!part.getSubmittedFileName().isEmpty()) {
            String imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            String now = CommonUtil.getImgDir();
            String realPath = req.getServletContext().getRealPath("/adminAvatar" + now);
            Path path = Paths.get(realPath);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            part.write(realPath + "/" + imageName);
            String image = String.format("adminAvatar%s/%s", now, imageName);
            System.out.println(image);
            user.setImage(image);
        }
        //update other
        user.setName(fullName);
        user.setPhone(phone);
        user.setGender(gender);
        user.setAddress(address);
        userDAO.update(user);
        req.getRequestDispatcher("/admin/account-profile.jsp").forward(req, resp);
    }
}
