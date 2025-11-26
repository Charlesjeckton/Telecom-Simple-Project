package dao;

import model.Subscription;
import util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAO {

    // =====================================================
    // GET CUSTOMER ID BY USER ID
    // =====================================================
    public int getCustomerIdByUserId(int userId) {
        String sql = "SELECT id FROM customers WHERE user_id = ?";
        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getInt("id");

        } catch (SQLException e) {
            System.err.println("ERROR getCustomerIdByUserId: " + e.getMessage());
        }
        return 0;
    }


    // =====================================================
    // GET SUBSCRIPTIONS BY CUSTOMER ID
    // =====================================================
    public List<Subscription> getSubscriptionsByCustomerId(int customerId) {
        List<Subscription> list = new ArrayList<>();

        String sql = "SELECT s.*, srv.name AS service_name "
                   + "FROM subscriptions s "
                   + "JOIN services srv ON s.service_id = srv.id "
                   + "WHERE s.customer_id = ? ORDER BY s.id DESC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Subscription s = new Subscription();
                s.setId(rs.getInt("id"));
                s.setCustomerId(rs.getInt("customer_id"));
                s.setServiceId(rs.getInt("service_id"));
                s.setServiceName(rs.getString("service_name"));
                s.setStartDate(rs.getString("start_date"));
                s.setEndDate(rs.getString("end_date"));
                s.setStatus(rs.getString("status"));
                list.add(s);
            }

        } catch (SQLException e) {
            System.err.println("ERROR getSubscriptionsByCustomerId: " + e.getMessage());
        }
        return list;
    }


    // =====================================================
    // ADD SUBSCRIPTION (Admin full version)
    // =====================================================
    public boolean addSubscription(int customerId, int serviceId, String startDate, String endDate) {

        String sql = "INSERT INTO subscriptions (customer_id, service_id, start_date, end_date, status) "
                   + "VALUES (?, ?, ?, ?, 'ACTIVE')";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, serviceId);
            stmt.setString(3, startDate);

            if (endDate == null || endDate.trim().isEmpty()) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setString(4, endDate);
            }

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("ERROR addSubscription (full): " + e.getMessage());
        }

        return false;
    }


    // =====================================================
    // ADD SUBSCRIPTION (Customer)
    // =====================================================
    public boolean addSubscription(int customerId, int serviceId, String startDate) {
        return addSubscription(customerId, serviceId, startDate, null);
    }


    // =====================================================
    // UPDATE SUBSCRIPTION (Admin)
    // =====================================================
    public boolean updateSubscription(int id, int customerId, int serviceId,
                                      String startDate, String endDate, String status) {

        String sql = "UPDATE subscriptions SET customer_id = ?, service_id = ?, "
                   + "start_date = ?, end_date = ?, status = ? WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            stmt.setInt(2, serviceId);
            stmt.setString(3, startDate);

            if (endDate == null || endDate.trim().isEmpty()) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setString(4, endDate);
            }

            stmt.setString(5, status.toUpperCase());
            stmt.setInt(6, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("ERROR updateSubscription: " + e.getMessage());
        }

        return false;
    }


    // =====================================================
    // ACTIVATE SUBSCRIPTION (Admin full)
    // =====================================================
    public boolean activateSubscription(int id, String startDate, String endDate) {

        String sql = "UPDATE subscriptions SET start_date = ?, end_date = ?, status = 'ACTIVE' WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, startDate);

            if (endDate == null || endDate.trim().isEmpty()) {
                stmt.setNull(2, Types.DATE);
            } else {
                stmt.setString(2, endDate);
            }

            stmt.setInt(3, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("ERROR activateSubscription (full): " + e.getMessage());
        }

        return false;
    }


    // =====================================================
    // ACTIVATE SUBSCRIPTION (Customer)
    // =====================================================
    public boolean activateSubscription(int id, String startDate) {
        return activateSubscription(id, startDate, null);
    }


    // =====================================================
    // DEACTIVATE (Customer/Admin)
    // =====================================================
    public boolean deactivateSubscription(int id) {
        String sql = "UPDATE subscriptions SET status = 'INACTIVE', end_date = CURDATE() WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("ERROR deactivateSubscription: " + e.getMessage());
        }

        return false;
    }


    // =====================================================
    // ADMIN — GET ALL SUBSCRIPTIONS
    // =====================================================
    public List<Subscription> getAllSubscriptions() {
        List<Subscription> list = new ArrayList<>();

        String sql = "SELECT s.*, c.name AS customer_name, srv.name AS service_name "
                   + "FROM subscriptions s "
                   + "JOIN customers c ON s.customer_id = c.id "
                   + "JOIN services srv ON s.service_id = srv.id "
                   + "ORDER BY s.id DESC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Subscription s = new Subscription();
                s.setId(rs.getInt("id"));
                s.setCustomerId(rs.getInt("customer_id"));
                s.setCustomerName(rs.getString("customer_name"));
                s.setServiceId(rs.getInt("service_id"));
                s.setServiceName(rs.getString("service_name"));
                s.setStartDate(rs.getString("start_date"));
                s.setEndDate(rs.getString("end_date"));
                s.setStatus(rs.getString("status"));
                list.add(s);
            }

        } catch (SQLException e) {
            System.err.println("ERROR getAllSubscriptions: " + e.getMessage());
        }

        return list;
    }


    // =====================================================
    // GET SUBSCRIPTION BY ID
    // =====================================================
    public Subscription getSubscriptionById(int id) {
        String sql = "SELECT s.*, srv.name AS service_name "
                   + "FROM subscriptions s "
                   + "JOIN services srv ON s.service_id = srv.id "
                   + "WHERE s.id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Subscription s = new Subscription();
                s.setId(rs.getInt("id"));
                s.setCustomerId(rs.getInt("customer_id"));
                s.setServiceId(rs.getInt("service_id"));
                s.setServiceName(rs.getString("service_name"));
                s.setStartDate(rs.getString("start_date"));
                s.setEndDate(rs.getString("end_date"));
                s.setStatus(rs.getString("status"));
                return s;
            }

        } catch (SQLException e) {
            System.err.println("ERROR getSubscriptionById: " + e.getMessage());
        }

        return null;
    }


    // =====================================================
    // CHECK CUSTOMER OWNS SUBSCRIPTION
    // =====================================================
    public boolean customerOwnsSubscription(int customerId, int subscriptionId) {
        String sql = "SELECT id FROM subscriptions WHERE id = ? AND customer_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, subscriptionId);
            stmt.setInt(2, customerId);

            return stmt.executeQuery().next();

        } catch (SQLException e) {
            System.err.println("ERROR customerOwnsSubscription: " + e.getMessage());
        }

        return false;
    }


    // =====================================================
    // GET ACTIVE SUBSCRIPTIONS (Billing)
    // =====================================================
    public List<Subscription> getActiveSubscriptionsByCustomer(int customerId) {
        List<Subscription> list = new ArrayList<>();

        String sql = "SELECT * FROM subscriptions WHERE customer_id = ? AND status = 'ACTIVE'";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Subscription s = new Subscription();
                s.setId(rs.getInt("id"));
                s.setCustomerId(rs.getInt("customer_id"));
                s.setServiceId(rs.getInt("service_id"));
                s.setStartDate(rs.getString("start_date"));
                s.setEndDate(rs.getString("end_date"));
                s.setStatus(rs.getString("status"));

                list.add(s);
            }

        } catch (SQLException e) {
            System.err.println("ERROR getActiveSubscriptionsByCustomer: " + e.getMessage());
        }

        return list;
    }


    // =====================================================
    // GET SERVICE ID FROM SUBSCRIPTION
    // =====================================================
    public int getServiceIdBySubscriptionId(int subscriptionId) {
        String sql = "SELECT service_id FROM subscriptions WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, subscriptionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getInt("service_id");

        } catch (SQLException e) {
            System.err.println("ERROR getServiceIdBySubscriptionId: " + e.getMessage());
        }

        return 0;
    }
}
