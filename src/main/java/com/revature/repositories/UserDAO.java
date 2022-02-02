package com.revature.repositories;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDAO {
    ConnectionFactory cf = ConnectionFactory.getInstance();
    /**
     * Should retrieve a User from the DB with the corresponding username or an empty optional if there is no match.
     */
    public Optional<User> getByUsername(String username) {
        String sql = "select * from users where username = ?";
        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("pass"),
                        Role.valueOf(rs.getString("role"))
                );
               return Optional.of(u);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * <ul>
     *     <li>Should Insert a new User record into the DB with the provided information.</li>
     *     <li>Should throw an exception if the creation is unsuccessful.</li>
     *     <li>Should return a User object with an updated ID.</li>
     * </ul>
     *
     * Note: The userToBeRegistered will have an id=0, and username and password will not be null.
     * Additional fields may be null.
     */
    public User create(User userToBeRegistered) {
        String sql = "insert into users values (default, ?, ?, ?, ?, ?, ?) returning *";

        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userToBeRegistered.getFirstname());
            ps.setString(2, userToBeRegistered.getLastname());
            ps.setString(3, userToBeRegistered.getEmail());
            ps.setString(4, userToBeRegistered.getUsername());
            ps.setString(5, userToBeRegistered.getPassword());
            ps.setString(6, userToBeRegistered.getRole().toString().toUpperCase());

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                userToBeRegistered.setId(rs.getInt("id"));
                return userToBeRegistered;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getById(Integer id) {
        String sql = "select * from users where id = ?";

        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("pass"),
                        Role.valueOf(rs.getString("role"))
                );
                return u;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<User> getByEmail(String email) {
        String sql = "select * from users where email = ?";
        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("pass"),
                        Role.valueOf(rs.getString("role"))
                );
                return Optional.of(u);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void update(User user) {
        String sql = "update users set first_name = ?, last_name = ?,email = ?, username = ?, pass = ?";
        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstname());
            ps.setString(2, user.getLastname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());

            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Integer id) {
        String sql = "delete from users where id = ?";
        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
