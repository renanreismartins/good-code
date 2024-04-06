package refactoring.client;

import refactoring.domain.Order;

public interface PaymentClient {
    void pay(Order order);
}
