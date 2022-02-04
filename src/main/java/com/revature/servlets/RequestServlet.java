package com.revature.servlets;

import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RequestServlet extends HttpServlet {
    private ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
    private UserDAO userDAO = new UserDAO();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect("newreimbursementrequest.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        Double amount = Double.valueOf(request.getParameter("amount"));
        String description = request.getParameter("description");

        Reimbursement newReimbursement = new Reimbursement(1, Status.PENDING, userDAO.getByUsername(session.getAttribute("username").toString()).get(), null, description, amount);
        reimbursementDAO.create(newReimbursement);
        response.sendRedirect("reimbursements.html");
    }
}
