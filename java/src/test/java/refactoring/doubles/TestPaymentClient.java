package refactoring.doubles;

import refactoring.domain.Order;
import refactoring.client.PaymentClient;

public class TestPaymentClient implements PaymentClient {
    private Order paid = null;

    public Order getPaidOrder() {
        return paid;
    }

    @Override
    public void pay(Order order) {
        this.paid = order;
    }
}
