package dao;

import model.Service;
import util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {

    // ======================================================
    // 1️⃣ Add a new service (ADMIN)
    // ======================================================
    public boolean addService(Service s) {
        String sql = "INSERT INTO services (name, description, monthly_fee, active) VALUES (?, ?, ?, 1)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getName());
            stmt.setString(2, s.getDescription());
            stmt.setDouble(3, s.getMonthlyFee());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding service: " + e.getMessage());
        }
        return false;
    }

    // ======================================================
    // 2️⃣ CUSTOMER — List ONLY active services
    // ======================================================
    public List<Service> getAllActiveServices() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM services WHERE active = 1 ORDER BY id DESC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Service s = new Service();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                s.setMonthlyFee(rs.getDouble("monthly_fee"));
                s.setActive(rs.getInt("active") == 1);
                list.add(s);
            }

        } catch (SQLException e) {
            System.err.println("Error listing active services: " + e.getMessage());
        }
        return list;
    }

    // ======================================================
    // 3️⃣ ADMIN — List ALL services
    // ======================================================
    public List<Service> getAllServices() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM services ORDER BY id DESC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Service s = new Service();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                s.setMonthlyFee(rs.getDouble("monthly_fee"));
                s.setActive(rs.getInt("active") == 1);
                list.add(s);
            }

        } catch (SQLException e) {
            System.err.println("Error listing all services: " + e.getMessage());
        }
        return list;
    }

    // ======================================================
    // 4️⃣ Get ONE service by ID
    // ======================================================
    public Service getServiceById(int id) {
        String sql = "SELECT * FROM services WHERE id = ?";
        Service service = null;

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                service = new Service();
                service.setId(rs.getInt("id"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setMonthlyFee(rs.getDouble("monthly_fee"));
                service.setActive(rs.getInt("active") == 1);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching service by ID: " + e.getMessage());
        }

        return service;
    }

    // ======================================================
    // 5️⃣ CUSTOMER — Services NOT subscribed
    // ======================================================
    public List<Service> getServicesNotSubscribed(int customerId) {
        List<Service> list = new ArrayList<>();

        String sql =
                "SELECT * FROM services " +
                        "WHERE active = 1 AND id NOT IN (" +
                        "   SELECT service_id FROM subscriptions WHERE customer_id = ?" +
                        ") ORDER BY id";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Service s = new Service();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                s.setMonthlyFee(rs.getDouble("monthly_fee"));
                s.setActive(rs.getInt("active") == 1);
                list.add(s);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching available services: " + e.getMessage());
        }

        return list;
    }

    // ======================================================
    // 6️⃣ AJAX — Toggle service active/inactive
    // ======================================================
    public boolean toggleStatus(int id) {
        String sql = "UPDATE services SET active = NOT active WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            System.err.println("ToggleStatus ERROR: " + e.getMessage());
        }
        return false;
    }

    // ======================================================
    // 7️⃣ ADMIN — Update service
    // ======================================================
    public boolean updateService(Service s) {
        String sql = "UPDATE services SET name=?, description=?, monthly_fee=?, active=? WHERE id=?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getName());
            stmt.setString(2, s.getDescription());
            stmt.setDouble(3, s.getMonthlyFee());
            stmt.setInt(4, s.isActive() ? 1 : 0);
            stmt.setInt(5, s.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating service: " + e.getMessage());
        }
        return false;
    }

    // ======================================================
    // 8️⃣ ADMIN — Hard delete
    // ======================================================
    public boolean deleteService(int id) {
        String sql = "DELETE FROM services WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting service: " + e.getMessage());
        }
        return false;
    }

    // ======================================================
    // 9️⃣ ADMIN — Soft delete (deactivate)
    // ======================================================
    public boolean deactivateService(int id) {
        String sql = "UPDATE services SET active = 0 WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deactivating service: " + e.getMessage());
        }
        return false;
    }
}
