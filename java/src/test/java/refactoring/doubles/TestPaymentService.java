package refactoring.doubles;

import refactoring.domain.Order;
import refactoring.service.PaymentService;

public class TestPaymentService implements PaymentService {
    private Order paid = null;

    public Order getPaidOrder() {
        return paid;
    }

    @Override
    public void pay(Order order) {
        this.paid = order;
    }
}
