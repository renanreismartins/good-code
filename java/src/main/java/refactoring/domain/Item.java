package refactoring.domain;

public class Item {
    private Integer productId;
    private int quantity;
    private Double taxedAmount;
    private Double tax;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTaxedAmount() {
        return taxedAmount;
    }

    public void setTaxedAmount(Double taxedAmount) {
        this.taxedAmount = taxedAmount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }
}
