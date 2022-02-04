package com.revature.servlets;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserDAO;
import com.revature.services.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private static UserDAO userDAO = new UserDAO();
    private static AuthService authService = new AuthService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("registration.html");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User newUser = new User(
                1,
                request.getParameter("firstname"),
                request.getParameter("lastname"),
                request.getParameter("email"),
                request.getParameter("username"),
                request.getParameter("password"),
                Role.valueOf(request.getParameter("role").toUpperCase())
        );
        if(authService.register(newUser) != null) {
            response.sendRedirect("reimbursements.html");
        }
    }
}
