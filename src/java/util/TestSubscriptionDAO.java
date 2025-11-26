package util;

import dao.SubscriptionDAO;

public class TestSubscriptionDAO {
    public static void main(String[] args) {
        SubscriptionDAO dao = new SubscriptionDAO();

        int subId = 1; // Use an existing subscription ID from DB
        String startDate = "2025-01-01"; // Use a valid test date

        if (dao.activateSubscription(subId, startDate)) {
            System.out.println("Subscription activated!");
        } else {
            System.out.println("Failed to activate subscription.");
        }
    }
}
