package refactoring.service;

import refactoring.domain.Order;

import java.util.Calendar;

public interface ShipmentService {
    Calendar calculateShipmentDate(Order order);
}
