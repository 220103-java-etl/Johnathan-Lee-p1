package com.revature.services;

import com.revature.exceptions.ReimbursementException;
import com.revature.exceptions.InvalidUsernameException;
import com.revature.exceptions.RegistrationUnsuccessfulException;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

import java.util.Optional;

/**
 * The AuthService should handle login and registration for the ERS application.
 *
 * {@code login} and {@code register} are the minimum methods required; however, additional methods can be added.
 *
 * Examples:
 * <ul>
 *     <li>Retrieve Currently Logged-in User</li>
 *     <li>Change Password</li>
 *     <li>Logout</li>
 * </ul>
 */
public class AuthService {
    private UserDAO userDAO = new UserDAO();
    /**
     * <ul>
     *     <li>Needs to check for existing users with username/email provided.</li>
     *     <li>Must throw exception if user does not exist.</li>
     *     <li>Must compare password provided and stored password for that user.</li>
     *     <li>Should throw exception if the passwords do not match.</li>
     *     <li>Must return user object if the user logs in successfully.</li>
     * </ul>
     */
    public User login(String username, String password) {
       Optional<User> user = userDAO.getByUsername(username);
       if(user.isPresent()) {
           if(username.equals(user.get().getUsername())) {
               if(user.get().getPassword().equals(password)) {
                   return user.get();
               } else {
                   ReimbursementException passwordException = new ReimbursementException("Invalid password.");
                   throw passwordException;
               }
           } else {
               InvalidUsernameException usernameException = new InvalidUsernameException("Invalid Username.");
               throw usernameException;
           }
       } else {
           InvalidUsernameException usernameException = new InvalidUsernameException("Username not found");
           throw usernameException;
       }
    }

    /**
     * <ul>
     *     <li>Should ensure that the username/email provided is unique.</li>
     *     <li>Must throw exception if the username/email is not unique.</li>
     *     <li>Should persist the user object upon successful registration.</li>
     *     <li>Must throw exception if registration is unsuccessful.</li>
     *     <li>Must return user object if the user registers successfully.</li>
     *     <li>Must throw exception if provided user has a non-zero ID</li>
     * </ul>
     *
     * Note: userToBeRegistered will have an id=0, additional fields may be null.
     * After registration, the id will be a positive integer.
     */
    public User register(User userToBeRegistered) {
       if(userDAO.getByUsername(userToBeRegistered.getUsername()).isPresent()) {
           RegistrationUnsuccessfulException usernameException = new RegistrationUnsuccessfulException("Username already exists");
           throw usernameException;
       } else if(userDAO.getByEmail(userToBeRegistered.getEmail()).isPresent()) {
           RegistrationUnsuccessfulException emailException = new RegistrationUnsuccessfulException("Email already exists");
           throw emailException;
       }
       else {
            return userDAO.create(userToBeRegistered);
       }
    }

    /**
     * This is an example method signature for retrieving the currently logged-in user.
     * It leverages the Optional type which is a useful interface to handle the
     * possibility of a user being unavailable.
     */
    public Optional<User> exampleRetrieveCurrentUser() {
        return Optional.empty();
    }
}
