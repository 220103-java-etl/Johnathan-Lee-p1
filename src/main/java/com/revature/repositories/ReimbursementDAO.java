package com.revature.repositories;

import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ReimbursementDAO {
    ConnectionFactory cf = ConnectionFactory.getInstance();
    UserDAO userDAO = new UserDAO();

    /**
     * Should retrieve a Reimbursement from the DB with the corresponding id or an empty optional if there is no match.
     */
    public Optional<Reimbursement> getById(int id) {
        String sql = "select * from reimbursement_requests where id = ?";

        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                Reimbursement r = new Reimbursement(
                        rs.getInt("id"),
                        Status.valueOf(rs.getString("status")),
                        userDAO.getById(rs.getInt("author_id")),
                        userDAO.getById(rs.getInt("resolver_id")),
                        rs.getString("description"),
                        rs.getDouble("amount")
                );
                return Optional.of(r);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Reimbursement> getAll() {
        String sql = "select * from reimbursement_requests";
        List<Reimbursement> reimbursementsList = new ArrayList<>();

        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Reimbursement r = new Reimbursement(
                        rs.getInt("id"),
                        Status.valueOf(rs.getString("status")),
                        userDAO.getById(rs.getInt("author_id")),
                        userDAO.getById(rs.getInt("resolver_id")),
                        rs.getString("description"),
                        rs.getDouble("amount")
                );
                reimbursementsList.add(r);
            }
            return reimbursementsList;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Reimbursement> getByUsername(String username) {
        String sql = "select * from reimbursement_requests where author_id = ?";
        List<Reimbursement> reimbursementList = new ArrayList<>();

        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            int id = userDAO.getByUsername(username).get().getId();
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Reimbursement r = new Reimbursement(
                    rs.getInt("id"),
                    Status.valueOf(rs.getString("status")),
                    userDAO.getById(rs.getInt("author_id")),
                    userDAO.getById(rs.getInt("resolver_id")),
                    rs.getString("description"),
                    rs.getDouble("amount")
                );
                reimbursementList.add(r);
            }
            return reimbursementList;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
     * Should retrieve a List of Reimbursements from the DB with the corresponding Status or an empty List if there are no matches.
     */
    public List<Reimbursement> getByStatus(Status status) {
        String sql = "select * from reimbursement_requests where status = ?";
        List<Reimbursement> reimbursementsList = new ArrayList<>();

        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status.toString());
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Reimbursement r = new Reimbursement(
                    rs.getInt("id"),
                    Status.valueOf(rs.getString("status")),
                    userDAO.getById(rs.getInt("author_id")),
                    userDAO.getById(rs.getInt("resolver_id")),
                    rs.getString("description"),
                    rs.getDouble("amount")
                );
                reimbursementsList.add(r);
            }
            return reimbursementsList;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Reimbursement updateReimbursementStatus(Reimbursement reimbursement, Status status, User resolver) {
        if(reimbursement.getStatus().equals(Status.PENDING)) {
            String sql = "update reimbursement_requests set Status = ?, resolver_id = ? where id = ?";
            try(Connection conn = cf.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, status.toString().toUpperCase());
                ps.setInt(2, resolver.getId());
                ps.setInt(3, reimbursement.getId());
                ps.execute();
            } catch(SQLException e) {
                e.printStackTrace();
            }
            if(status.toString().equalsIgnoreCase("approved")) {
                userDAO.update(reimbursement.getAuthor());
            }
            return reimbursement;
        }
        return reimbursement;

    }

    public Reimbursement create(Reimbursement newReimbursementRequest) {
        String sql = "insert into reimbursement_requests values (default, ?, ?, ?, ?, ?) returning *";

        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newReimbursementRequest.getStatus().toString().toUpperCase());
            ps.setInt(2, newReimbursementRequest.getAuthor().getId());
            ps.setObject(3, null);
            ps.setString(4, newReimbursementRequest.getDescription());
            ps.setDouble(5, newReimbursementRequest.getAmount());

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                newReimbursementRequest.setId(rs.getInt("id"));
                return newReimbursementRequest;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <ul>
     *     <li>Should Update an existing Reimbursement record in the DB with the provided information.</li>
     *     <li>Should throw an exception if the update is unsuccessful.</li>
     *     <li>Should return a Reimbursement object with updated information.</li>
     * </ul>
     */
    public Reimbursement update(Reimbursement unprocessedReimbursement) {
        String sql = "update reimbursement_requests set status = ?, author_id = ?, resolver_id = ?, description = ?, amount = ? where id = ?";

        try(Connection conn = cf.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, unprocessedReimbursement.getStatus().toString().toUpperCase());
            ps.setInt(2, unprocessedReimbursement.getAuthor().getId());
            ps.setInt(3, unprocessedReimbursement.getResolver().getId());
            ps.setString(4, unprocessedReimbursement.getDescription());
            ps.setDouble(5, unprocessedReimbursement.getAmount());
            ps.setInt(6, unprocessedReimbursement.getId());

            ps.execute();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return unprocessedReimbursement;
    }
}
