package refactoring.service;

import refactoring.domain.Order;

public interface PaymentService {
    void pay(Order order);
}
