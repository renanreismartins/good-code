package refactoring.controller;

import refactoring.domain.Order;
import refactoring.repository.OrderRepository;
import refactoring.service.ShipmentService;

import java.util.Calendar;

import static refactoring.domain.OrderStatus.CREATED;
import static refactoring.domain.OrderStatus.SHIPPED;

public class OrderShipmentController {
    private final OrderRepository orderRepository;
    private final ShipmentService shipmentService;

    public OrderShipmentController(OrderRepository orderRepository, ShipmentService shipmentService) {
        this.orderRepository = orderRepository;
        this.shipmentService = shipmentService;
    }

    public Response post(OrderShipmentRequest request) {
        final Order order = orderRepository.getById(request.getOrderId());

        if (order.getStatus().equals(CREATED)) {
            throw new OrderCannotBeShippedException();
        }

        if (order.getStatus().equals(SHIPPED)) {
            throw new OrderCannotBeShippedTwiceException();
        }

        Calendar shipmentDate = shipmentService.calculateShipmentDate(order);

        order.setShipmentDate(shipmentDate);
        orderRepository.save(order);

        return new Response(200);
    }
}
