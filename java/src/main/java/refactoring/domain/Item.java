package refactoring.domain;

public class Item {
    private Product product;
    private int quantity;
    private Double taxedAmount;
    private Double tax;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
