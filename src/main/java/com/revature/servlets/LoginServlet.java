package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.services.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet{
    private static AuthService authService = new AuthService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = authService.login(username, password);
        if(user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole().toString());

            response.sendRedirect("reimbursements.html");
        }
    }
}