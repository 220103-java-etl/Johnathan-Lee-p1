package com.revature.servlets;

import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.UserDAO;
import com.revature.services.ReimbursementService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

public class AdminServlet extends HttpServlet {
    private ReimbursementService reimbursementService = new ReimbursementService();
    private ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
    private UserDAO userDAO = new UserDAO();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       response.sendRedirect("reimbursementadmin.html");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if(session.getAttribute("role").toString().equalsIgnoreCase("finance manager")) {
            String data = request.getReader().lines().collect(Collectors.joining());
            String[] dataArray = data.split("\\t|,|;|\\.|\\?|!|-|:|@|\\[|\\]|\\(|\\)|\\{|\\}|_|\\*|/");
//        for(int i = 0; i < dataArray.length; i++) {
//            System.out.println(i + ": " + dataArray[i]);
//        }
            Integer id = Integer.parseInt(dataArray[2]);
            String status = dataArray[4].replaceAll("\"", "");
            String username = dataArray[6].replaceAll("\"", "");
            Reimbursement updatedReimbursement = reimbursementService.process(reimbursementDAO.getById(id).get(),
                    Status.valueOf(status),
                    userDAO.getByUsername(session.getAttribute("username").toString()).get()
            );
            response.sendRedirect("reimbursements.html");
        } else {
            response.sendRedirect("reimbursements.html");
        }
    }
}
