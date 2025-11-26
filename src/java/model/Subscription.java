package model;

public class Subscription {

    private int id;
    private int customerId;
    private int serviceId;

    // Extra display fields for admin & customer views
    private String customerName;
    private String serviceName;

    private String startDate;
    private String endDate;   // supports nullable end date
    private String status;

    // ===== Constructors =====
    public Subscription() {}

    // Full constructor with endDate support
    public Subscription(int customerId, int serviceId, String startDate, String endDate, String status) {
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Older constructor kept for backward compatibility
    public Subscription(int customerId, int serviceId, String startDate, String status) {
        this(customerId, serviceId, startDate, null, status);
    }

    // ===== Getters & Setters =====

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {     
        return endDate;
    }

    public void setEndDate(String endDate) {   
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ===== Debugging Print =====
    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", serviceId=" + serviceId +
                ", customerName='" + customerName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
