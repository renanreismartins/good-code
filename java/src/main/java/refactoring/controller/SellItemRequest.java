package refactoring.controller;

public class SellItemRequest {
    private int quantity;
    private Integer id;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Integer getId() {
        return id;
    }
}
