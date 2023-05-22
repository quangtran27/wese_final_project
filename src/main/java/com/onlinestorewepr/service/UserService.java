package com.onlinestorewepr.service;

import com.onlinestorewepr.dao.*;
import com.onlinestorewepr.entity.Cart;
import com.onlinestorewepr.entity.User;
import com.onlinestorewepr.util.CSRFTokenGenerator;
import com.onlinestorewepr.util.CommonUtil;
import com.onlinestorewepr.util.MessageUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;

public class UserService {
    private final UserDAO userDAO;
    private final MessageUtil message;

    HttpServletResponse resp;
    HttpServletRequest req;

    public UserService(HttpServletRequest req, HttpServletResponse resp) {
        this.req = req;
        this.resp = resp;
        this.message = new MessageUtil();
        userDAO = new UserDAO();
    }

    public User authenticate(String username, String password) {
        User user = userDAO.get(username);
        if (user != null && !user.isAdmin()) {
            if (password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
    public boolean checkEmail(String email) {
        User user = userDAO.findUserByEmail(email);
        return user != null;
    }
    public void userRegister() throws IOException,ServletException {
        String name = req.getParameter("fullName");
        String username = req.getParameter("usernameNew");
        String password = req.getParameter("passwordNew");
        String passwordReenter = req.getParameter("passwordNewRetype");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String gender = req.getParameter("gender");
        String message = "";

        //Check user is in database?
        User userCreated = userDAO.findUserCreated(username);

        //Check enter
        if (name == null || phone == null || username == null || password == null || email == null || gender == null) {
            if (Objects.equals(name, "")) {
                message ="Please enter full name!";
            }
            else if (Objects.equals(username, "")) {
                message ="Please enter username!";
            }
            else if (Objects.equals(phone, "")) {
                message ="Please enter phone number!";
            }
            else if (Objects.equals(email, "")) {
                message ="Please enter email!";
            }
            else if (Objects.equals(password, "")) {
                message ="Please enter password!";
            }
            else if (Objects.equals(passwordReenter, "")) {
                message ="Please enter confirm password!";
            }
            else if (gender == null) {
                message = "Please choose gender!";
            }
            req.setAttribute("messageRegisterFail",message);
            req.setAttribute("action","signup");
            req.setAttribute("action", "signup");
            req.getRequestDispatcher("/web/authentication.jsp").forward(req,resp);
        }
        else {
            boolean check = true;
            //Check user is in database?
            if (userCreated != null){
                check = false;
                message = "This account has already existed!";
                req.setAttribute("action","signup");
                req.setAttribute("messageRegisterFail", message);
                req.getRequestDispatcher("/web/authentication.jsp").forward(req,resp);
            }
            // Check phone value
            for (int i = 0; i < phone.length(); i++)
            {
                if (!(phone.charAt(i) <= '9' && phone.charAt(i) >= '0' )) {
                    check = false;
                    message = "Phone number is not valid! Please re-enter!";
                    req.setAttribute("action","signup");
                    req.setAttribute("messageRegisterFail",message);
                    req.getRequestDispatcher("/web/authentication.jsp").forward(req,resp);
                }
            }
            //Check Phone length
            if (phone.length() != 10) {
                check = false;
                message = "Phone number length must be 10 numbers! Please re-enter!";
                req.setAttribute("action","signup");
                req.setAttribute("messageRegisterFail",message);
                req.getRequestDispatcher("/web/authentication.jsp").forward(req,resp);
            }
            // check Email
            if (checkEmail(email)) {
                check = false;
                message= "Email has been used to register!";
                req.setAttribute("action","signup");
                req.setAttribute("messageRegisterFail",message);
                req.getRequestDispatcher("/web/authentication.jsp").forward(req,resp);
            }
            // Check Password length
            if (password.length() < 8) {
                check = false;
                message = "Password must be at least 8 characters! Please re-enter!";
                req.setAttribute("action","signup");
                req.setAttribute("messageRegisterFail",message);
                req.getRequestDispatcher("/web/authentication.jsp").forward(req,resp);
            }
            //Check Password type
            boolean number = false, lowercase = false, uppercase = false, special = false;
            for (int i = 0; i < password.length(); i++) {
                char x = password.charAt(i);
                if (x <= '9' && x >= '0') number = true;
                else if (x <= 'z' && x >= 'a') lowercase = true;
                else if (x <= 'Z' && x >= 'A') uppercase = true;
                else special = true;
            }

            //Inform error on Form
            if (!number || !lowercase || !uppercase || !special) {
                if (!number) {
                    check = false;
                    message = "Password must include numbers! Please re-enter!";
                }
                else if (!lowercase) {
                    check = false;
                    message = "Password must include lowercase characters! Please re-enter!";
                }
                else if (!uppercase) {
                    check = false;
                    message = "Password must include uppercase characters! Please re-enter!";
                }
                else {
                    check = false;
                    message = "Password must include special characters! Please re-enter!";
                }
                req.setAttribute("action","signup");
                req.setAttribute("messageRegisterFail",message);
                req.getRequestDispatcher("/web/authentication.jsp").forward(req,resp);
            }
            if (!password.equals(passwordReenter)){
                check = false;
                message = "Password and Confirm password do not match!";
                req.setAttribute("action","signup");
                req.setAttribute("messageRegisterFail",message);
                req.getRequestDispatcher("/web/authentication.jsp").forward(req,resp);
            }
            if (password.contains(name)) {
                check = false;
                message = "Passwords cannot contain personal names! Please re-enter!";
                req.setAttribute("action","signup");
                req.setAttribute("messageRegisterFail",message);
                req.getRequestDispatcher("/web/authentication.jsp").forward(req,resp);
            }

            if(check) {
                //Created User
                User userNew = new User();
                userNew.setUsername(username);
                if(!password.equals("")){
                    userNew.setPassword(password);
                }
                userNew.setName(name);
                userNew.setGender(gender);
                userNew.setPhone(phone);
                userNew.setEmail(email);
                userDAO.insert(userNew);

                Cart cart = new Cart();
                new CartDAO().insert(cart);
                userNew.setCart(cart);
                userDAO.update(userNew);

                //Inform success in form
                message= "Create success! Sign in to get started";
                req.setAttribute("action","signup");
                req.setAttribute("messageRegisterSuccess", message);
                req.getRequestDispatcher("/web/authentication.jsp").forward(req,resp);
            }
        }
    }

    protected  void saveRememberMe(HttpServletResponse response, String userName, String password){
        Cookie username = new Cookie("username",userName);
        Cookie pass = new Cookie("password",password);
        username.setMaxAge(60 * 60 * 24 * 365 * 3);
        pass.setMaxAge(60 * 60 * 24 * 365 * 3);
        response.addCookie(username);
        response.addCookie(pass);
        response.setContentType("text/html");
    }

    public void login() throws IOException, ServletException {
        //Get param from form
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        String errMessage = "";
        boolean hasErr = false;

        //Check account in database
        User user = authenticate(username, password);
        User userCreated = userDAO.get(username);
        boolean isRememberMe = "on".equals(remember);

        //Check enter
        if(username == null ||password==null || username.length()==0 || password.length()==0){
            hasErr= true;
            errMessage ="Username & Password cannot be empty!";
        }
        else {
            try {
                //Enter Wrong username/password
                if (user == null) {
                    hasErr = true;
                    errMessage ="Username & Password is not correct!";
                }
                if (userCreated == null) {
                    hasErr = true;
                    errMessage ="Account does not exist!";
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                hasErr = true;
                errMessage = e.getMessage();
            }
        }

        //Display status on web
        if (hasErr) {
            req.setAttribute("message",errMessage);
            req.getRequestDispatcher("/web/authentication.jsp").forward(req,resp);
        }
        else { // Login successfully
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(3600); // 1 hour

            String CSRF = CSRFTokenGenerator.generateToken();
            session.setAttribute("csrfToken", CSRF);
            session.setAttribute("userLogged", user);
            if (isRememberMe) {
                saveRememberMe(resp, username, password);
            }
            // Forward to home page
            resp.sendRedirect("/home");
        }
    }

    public void showProfile() throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        OrderItemDAO itemDAO = new OrderItemDAO();
        ProductDAO productDAO = new ProductDAO();
        req.setAttribute("orderDAO",orderDAO);
        req.setAttribute("ItemDAO",itemDAO);
        req.setAttribute("productDAO",productDAO);

        User userLogged = (User)req.getSession().getAttribute("userLogged");
        User user = new UserDAO().get(userLogged.getUsername());
        req.setAttribute("user", user);
        resp.setContentType("text/html;charset=UTF-8");
        req.getRequestDispatcher("/web/profile.jsp").forward(req,resp);
    }

    public void showEditUserProfile() throws ServletException, IOException{
        User userLogged = (User)req.getSession().getAttribute("userLogged");
        User user = new UserDAO().get(userLogged.getUsername());
        req.setAttribute("user", user);
        req.getRequestDispatcher("/web/edit-profile.jsp").forward(req,resp);
    }

    public void updateUserProfile() throws ServletException, IOException{
        resp.setContentType("text/html;charset=UTF-8");
        User user = (User) req.getSession().getAttribute("userLogged");
        String fullName = req.getParameter("name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String gender = req.getParameter("sex");
        String address = req.getParameter("address");
        Part part = req.getPart("image");

        //Update image
        if (!part.getSubmittedFileName().isEmpty()){
            String imageName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
            String now = CommonUtil.getImgDir();
            String realPath = req.getServletContext().getRealPath("/imagesAvatar" + now);
            Path path = Paths.get(realPath);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            part.write(realPath + "/" + imageName);
            String image = String.format("imagesAvatar%s/%s", now, imageName);
            System.out.println(image);
            user.setImage(image);
        }
        //update mail
        if(!Objects.equals(user.getEmail(), email) && !checkEmail(email)){
            user.setEmail(email);
        }
        //update other
        user.setName(fullName);
        user.setPhone(phone);
        user.setGender(gender);
        user.setAddress(address);
        userDAO.update(user);

        //forward to profile
        showProfile();
    }

    public void changeUserPassword() throws ServletException,IOException {
        String messageBody, messageType;
        String username = req.getParameter("username");
        String oldPass = req.getParameter("password-old");
        String newPass = req.getParameter("password-new");
        String passRetype = req.getParameter("password-retype");

        //Get user to change
        User user = userDAO.get(username);

        //Validate enter
        if(Objects.equals(oldPass, "") || Objects.equals(newPass, "") || Objects.equals(passRetype, "")) {
            messageBody = "Please enter information!";
            messageType = "danger";
        }
        else if (!oldPass.trim().equals(user.getPassword())) {
            messageBody = "Present password is incorrect, please re-enter!";
            messageType = "danger";
        }
        else {
            //Validate password
            boolean number = false, lowercase = false, uppercase = false, special = false;
            for (int i = 0; i < newPass.length(); i++) {
                char x = newPass.charAt(i);
                if (x <= '9' && x >= '0') number = true;
                else if (x <= 'z' && x >= 'a') lowercase = true;
                else if (x <= 'Z' && x >= 'A') uppercase = true;
                else special = true;
            }
            //Inform error on Form
            if (!number || !lowercase|| !uppercase || !special) {
                if (!number) {
                    messageBody = "Password must include numbers! Please re-enter!";
                    messageType = "danger";

                }
                else if (!lowercase) {
                    messageBody = "Password must include lowercase characters! Please re-enter!";
                    messageType = "danger";
                }
                else if (!uppercase) {
                    messageBody = "Password must include uppercase characters! Please re-enter!";
                    messageType = "danger";
                }
                else {
                    messageBody = "Password must include special characters! Please re-enter!";
                    messageType = "danger";
                }
            }
            else {
                if (Objects.equals(oldPass, newPass)) {
                    messageBody = "The new password cannot be the same as the old password!";
                    messageType = "danger";
                }
                else if (!Objects.equals(newPass, passRetype)){
                    messageBody = "Password re-entered is incorrect!";
                    messageType = "danger";
                }
                else {
                    resp.setContentType("text/html;charset=UTF-8");
                    User usernew = (User) req.getSession().getAttribute("userLogged");
                    usernew.setPassword(newPass);
                    userDAO.update(usernew);
                    req.getSession().removeAttribute("userLogged");
                    messageBody = "Successful change! Sign in again to continue!";
                    messageType = "success";
                }
            }
        }
        message.setBody(messageBody);
        message.setType(messageType);
        req.setAttribute("user", user);
        req.setAttribute("message", message);
        req.getRequestDispatcher("/web/change_pass.jsp").forward(req, resp);
    }
    public void sendEmail() throws ServletException,IOException {
        String email = req.getParameter("email");
        String messageBody="",messageType="";
        RequestDispatcher dispatcher;
        int otpvalue;
        HttpSession mySession = req.getSession();

        //Get user to update password
        User user = userDAO.findUserByEmail(email);

        //Validate enter
        if (email.equals("")) {
            messageBody = "Please enter your email!";
            messageType = "danger";
        }
        else if (user != null) {
            // Random otp
            Random rand = new Random();
            otpvalue = rand.nextInt(1255650);

            // Get the session object
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("phand613@gmail.com", "hrlzgsmcfrormdbb");
                }
            });
            // compose message
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(email));// change accordingly
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject("MALE FASHION - OTP TO UPDATE YOUR PASSWORD");
                message.setText("Your OTP is: " + otpvalue);
                // send message
                Transport.send(message);
            }

            catch (MessagingException e) {
                e.printStackTrace();
            }
            dispatcher = req.getRequestDispatcher("EnterOtp.jsp");
            req.setAttribute("message","OTP is sent to your email.Please check!");
            mySession.setAttribute("otp",otpvalue);
            mySession.setAttribute("email",email);
            dispatcher.forward(req, resp);
        }
        else {
            //Email not register before
            messageBody = "Email does not register before!";
            messageType = "danger";
        }
        message.setBody(messageBody);
        message.setType(messageType);
        req.setAttribute("message", message);
        req.getRequestDispatcher("/web/forgetpass.jsp").forward(req, resp);
    }

    public void changePassForgot() throws ServletException,IOException {
        String messageBody, messageType;
        HttpSession session = req.getSession();
        String newPassword = req.getParameter("password");
        String confPassword = req.getParameter("confPassword");
        String userEmail = (String) session.getAttribute("email");

        User user = userDAO.findUserByEmail(userEmail);

        if (newPassword == null || confPassword == null){
            messageBody = "Please enter password!";
            messageType = "danger";
        }
        else {
            boolean number = false, lowercase = false, uppercase = false, special = false;
            for (int i = 0; i < newPassword.length(); i++) {
                char x = newPassword.charAt(i);
                if (x <= '9' && x >= '0') number = true;
                else if (x <= 'z' && x >= 'a') lowercase = true;
                else if (x <= 'Z' && x >= 'A') uppercase = true;
                else special = true;
            }
            //Inform error on Form
            if (!number || !lowercase || !uppercase || !special) {
                if (!number) {
                    messageBody = "Password must include numbers! Please re-enter!";
                    messageType = "danger";
                }
                else if (!lowercase) {
                    messageBody = "Password must include lowercase characters! Please re-enter!";
                    messageType = "danger";
                }
                else if (!uppercase) {
                    messageBody = "Password must include uppercase characters! Please re-enter!";
                    messageType = "danger";
                }
                else {
                    messageBody = "Password must include special characters! Please re-enter!";
                    messageType = "danger";
                }
            }
            else {
                int len = newPassword.length();
                if (len < 8) {
                    messageBody = "The new password must be more than 8 characters!";
                    messageType = "danger";
                } else if (!Objects.equals(newPassword, confPassword)){
                    messageBody = "Password re-entered is incorrect!";
                    messageType = "danger";
                }
                else {
                    resp.setContentType("text/html;charset=UTF-8");
                    user.setPassword(newPassword);
                    userDAO.update(user);
                    messageBody = "Successful change! Sign in to continue";
                    messageType = "success";
                }
            }
        }
        message.setBody(messageBody);
        message.setType(messageType);
        req.setAttribute("message", message);
        req.getRequestDispatcher("/web/newPassword.jsp").forward(req, resp);
    }
    public void validateOTP() throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String optGet = req.getParameter("otp");
        for (int i = 0; i < optGet.length(); i++) {
            if ( !(optGet.charAt(i) <= '9' && optGet.charAt(i) >= '0' )) {
                req.setAttribute("message","Please enter number!");
                req.getRequestDispatcher("EnterOtp.jsp").forward(req, resp);
            }
        }
        int value = Integer.parseInt(req.getParameter("otp"));
        HttpSession session = req.getSession();
        int otp =(int)session.getAttribute("otp");

        if (value == otp) {
            req.setAttribute("email", req.getParameter("email"));
            req.setAttribute("status", "success");
            dispatcher=req.getRequestDispatcher("newPassword.jsp");
            dispatcher.forward(req, resp);
        }
        else {
            req.setAttribute("message","Wrong OTP. Please enter again!");
            dispatcher=req.getRequestDispatcher("EnterOtp.jsp");
            dispatcher.forward(req, resp);
        }
    }
    public void updateService() {
        req.getSession();
        String username = req.getParameter("username");
        User user = userDAO.get(username);
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setAddress(address);
        userDAO.update(user);
        //
    }
    public void listAll() throws ServletException, IOException {
        List<User> userList = userDAO.getAll();
        req.setAttribute("userList", userList);
        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }
    public void getUser(String username) throws ServletException, IOException {
        User user = userDAO.get(username);
        System.out.println(user.getName());
        req.setAttribute("user", user);
        req.getRequestDispatcher("update-account.jsp").forward(req, resp);
    }
}