package util;

import dao.BillingDAO;
import model.Billing;

public class TestBillingDAO {
    public static void main(String[] args) {
        BillingDAO dao = new BillingDAO();
        Billing bill = new Billing();
        bill.setCustomerId(1);
        bill.setAmount(1200);
        bill.setBillingDate("2025-11-22");
        bill.setPaid(false);

        if (dao.generateBill(bill)) {
            System.out.println("Bill generated successfully!");
        } else {
            System.out.println("Failed to generate bill.");
        }
    }
}
