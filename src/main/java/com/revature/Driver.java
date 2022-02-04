package com.revature;

import com.revature.models.Reimbursement;
import com.revature.models.Role;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.UserDAO;
import com.revature.services.AuthService;
import com.revature.services.ReimbursementService;

import java.util.Optional;

public class Driver {
    private static UserDAO userDAO = new UserDAO();
    private static AuthService authService = new AuthService();
    private static ReimbursementDAO reimbursementDAO = new ReimbursementDAO();
    private static ReimbursementService reimbursementService = new ReimbursementService();

    public static void main(String[] args) {
//        User newUser = new User(0, "firstname", "lastname", "email", "user1", "pass1", Role.EMPLOYEE);
//        userDAO.create(newUser);
//        Optional<User> newUser = userDAO.getByUsername("user1");
//        Reimbursement r = new Reimbursement(1, Status.PENDING, newUser.get(), null, "certification training", 500.00);
//        reimbursementDAO.create(r);
//        System.out.println(userDAO.getByUsername("1"));
    }
}
