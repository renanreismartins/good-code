package refactoring.controller;

import refactoring.domain.Order;
import refactoring.domain.OrderStatus;
import refactoring.repository.OrderRepository;
import refactoring.service.ShipmentService;

import java.time.LocalDateTime;

import static refactoring.domain.OrderStatus.CREATED;
import static refactoring.domain.OrderStatus.SHIPPED;

public class OrderShipmentController {
    private final OrderRepository orderRepository;
    private final ShipmentService shipmentService;

    public OrderShipmentController(OrderRepository orderRepository, ShipmentService shipmentService) {
        this.orderRepository = orderRepository;
        this.shipmentService = shipmentService;
    }

    public Response run(OrderShipmentRequest request) {
        final Order order = orderRepository.getById(request.getOrderId());

        if (order.getStatus().equals(CREATED)) {
            throw new OrderCannotBeShippedException();
        }

        if (order.getStatus().equals(SHIPPED)) {
            throw new OrderCannotBeShippedTwiceException();
        }

        shipmentService.ship(order);

        order.setStatus(OrderStatus.SHIPPED);
        order.setShipmentDate(LocalDateTime.now());
        orderRepository.save(order);

        return new Response(200);
    }
}
