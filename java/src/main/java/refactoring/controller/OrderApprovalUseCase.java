package refactoring.controller;

import refactoring.domain.Order;
import refactoring.domain.OrderStatus;
import refactoring.repository.OrderRepository;

import java.time.LocalDateTime;

public class OrderApprovalUseCase {
    private final OrderRepository orderRepository;

    public OrderApprovalUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void run(OrderApprovalRequest request) {
        final Order order = orderRepository.getById(request.getOrderId());

        if (order.getStatus().equals(OrderStatus.SHIPPED)) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (request.isApproved() && order.getStatus().equals(OrderStatus.APPROVED)) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        if (!request.isApproved()) {
            throw new ApprovedOrderCannotBeRejectedException();
        }

        order.setStatus(OrderStatus.APPROVED);
        order.setApprovalDate(LocalDateTime.now());
        orderRepository.save(order);
    }
}
