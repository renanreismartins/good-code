package refactoring.repository;

import refactoring.domain.Order;

public interface OrderRepository {
    void save(Order order);

    Order getById(int orderId);
}
