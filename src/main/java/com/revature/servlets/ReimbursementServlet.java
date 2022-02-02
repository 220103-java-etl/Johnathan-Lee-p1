package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.services.ReimbursementService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ReimbursementServlet extends HttpServlet {
    ReimbursementService reimbursementService = new ReimbursementService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        List<Reimbursement> reimbursementList = reimbursementService.getAll();

        if(session.getAttribute("role").toString().equalsIgnoreCase("employee")) {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(reimbursementList);
            response.setContentType("application/json");
            response.getWriter().write(json);
        } else {
            response.sendRedirect("");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}
