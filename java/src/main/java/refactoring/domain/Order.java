package refactoring.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private Double total;
    private String currency;
    private List<Item> items;
    private Double tax;
    private OrderStatus status;
    private LocalDateTime creationDate;
    private LocalDateTime approvalDate;
    private LocalDateTime shipmentDate;
    private LocalDateTime rejectionDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public LocalDateTime getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDateTime shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public LocalDateTime getRejectionDate() {
        return rejectionDate;
    }

    public void setRejectionDate(LocalDateTime rejectionDate) {
        this.rejectionDate = rejectionDate;
    }
}
