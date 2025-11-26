package dao;

import model.Billing;
import util.DBConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

    // ============================================================
    // 1️⃣ Generate a bill
    // ============================================================
    public boolean generateBill(Billing bill) {
        String sql = "INSERT INTO billing (customer_id, service_id, amount, billing_date, paid) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bill.getCustomerId());
            stmt.setInt(2, bill.getServiceId());
            stmt.setDouble(3, bill.getAmount());
            stmt.setString(4, bill.getBillingDate());
            stmt.setBoolean(5, bill.isPaid());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error generating bill: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // 2️⃣ List all bills
    // ============================================================
    public List<Billing> getAllBills() {
        List<Billing> list = new ArrayList<>();

        String sql = "SELECT b.*, s.name AS service_name "
                   + "FROM billing b "
                   + "LEFT JOIN services s ON b.service_id = s.id "
                   + "ORDER BY b.id DESC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(extractBillingFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching all bills: " + e.getMessage());
        }

        return list;
    }

    // ============================================================
    // 3️⃣ Bills by customer
    // ============================================================
    public List<Billing> getBillsByCustomer(int customerId) {
        List<Billing> list = new ArrayList<>();

        String sql = "SELECT b.*, s.name AS service_name "
                   + "FROM billing b "
                   + "LEFT JOIN services s ON b.service_id = s.id "
                   + "WHERE b.customer_id = ? "
                   + "ORDER BY b.id DESC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(extractBillingFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching customer bills: " + e.getMessage());
        }

        return list;
    }

    // ============================================================
    // 4️⃣ Unpaid bills (simple)
    // ============================================================
    public List<Billing> getUnpaidBills() {
        List<Billing> list = new ArrayList<>();

        String sql = "SELECT b.*, s.name AS service_name "
                   + "FROM billing b "
                   + "LEFT JOIN services s ON b.service_id = s.id "
                   + "WHERE b.paid = 0 "
                   + "ORDER BY b.id DESC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(extractBillingFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching unpaid bills: " + e.getMessage());
        }

        return list;
    }

    // ============================================================
    // 5️⃣ Paid bills (simple)
    // ============================================================
    public List<Billing> getPaidBills() {
        List<Billing> list = new ArrayList<>();

        String sql = "SELECT b.*, s.name AS service_name "
                   + "FROM billing b "
                   + "LEFT JOIN services s ON b.service_id = s.id "
                   + "WHERE b.paid = 1 "
                   + "ORDER BY b.id DESC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(extractBillingFromResultSet(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching paid bills: " + e.getMessage());
        }

        return list;
    }

    // ============================================================
    // 6️⃣ Mark bill as paid
    // ============================================================
    public boolean markBillAsPaid(int billId) {
        String sql = "UPDATE billing SET paid = 1 WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error marking bill as paid: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // 7️⃣ Mark bill as unpaid
    // ============================================================
    public boolean markBillAsUnpaid(int billId) {
        String sql = "UPDATE billing SET paid = 0 WHERE id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error marking bill as unpaid: " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // 8️⃣ Unpaid bills WITH customer info
    // ============================================================
    public List<Billing> getUnpaidBillsWithCustomer() {
        List<Billing> list = new ArrayList<>();

        String sql = "SELECT b.*, c.name AS customer_name, c.email AS customer_email, "
                   + "s.name AS service_name "
                   + "FROM billing b "
                   + "LEFT JOIN customers c ON b.customer_id = c.id "
                   + "LEFT JOIN services s ON b.service_id = s.id "
                   + "WHERE b.paid = 0 "
                   + "ORDER BY b.id DESC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Billing bill = extractBillingFromResultSet(rs);
                bill.setCustomerName(rs.getString("customer_name"));
                bill.setCustomerEmail(rs.getString("customer_email"));
                list.add(bill);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching unpaid bills with customer info: " + e.getMessage());
        }

        return list;
    }

    // ============================================================
    // 9️⃣ Paid bills WITH customer + service info
    // ============================================================
    public List<Billing> getPaidBillsWithCustomer() {
        List<Billing> list = new ArrayList<>();

        String sql = "SELECT b.*, c.name AS customer_name, c.email AS customer_email, "
                   + "s.name AS service_name "
                   + "FROM billing b "
                   + "JOIN customers c ON b.customer_id = c.id "
                   + "JOIN services s ON b.service_id = s.id "
                   + "WHERE b.paid = 1 "
                   + "ORDER BY b.billing_date DESC";

        try (Connection conn = DBConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Billing bill = extractBillingFromResultSet(rs);

                bill.setCustomerName(rs.getString("customer_name"));
                bill.setCustomerEmail(rs.getString("customer_email"));
                bill.setServiceName(rs.getString("service_name"));

                list.add(bill);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching paid bills with customer info: " + e.getMessage());
        }

        return list;
    }

    // ============================================================
    // Helper: Extract Billing object safely
    // ============================================================
    private Billing extractBillingFromResultSet(ResultSet rs) throws SQLException {
        Billing bill = new Billing();

        bill.setId(rs.getInt("id"));
        bill.setCustomerId(rs.getInt("customer_id"));
        bill.setServiceId(rs.getInt("service_id"));
        bill.setAmount(rs.getDouble("amount"));
        bill.setBillingDate(rs.getString("billing_date"));
        bill.setPaid(rs.getBoolean("paid"));

        try {
            bill.setServiceName(rs.getString("service_name"));
        } catch (SQLException ignored) {}

        return bill;
    }
}
