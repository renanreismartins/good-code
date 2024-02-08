package refactoring.service;

import refactoring.domain.Order;

public interface ShipmentService {
    void ship(Order order);
}
