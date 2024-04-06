package refactoring.controller;

import refactoring.domain.Order;
import refactoring.domain.OrderStatus;
import refactoring.repository.OrderRepository;
import refactoring.service.PaymentService;

import java.time.LocalDateTime;
import java.util.Calendar;

public class OrderApprovalController {
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    public OrderApprovalController(OrderRepository orderRepository, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
    }

    public Response post(OrderApprovalRequest request) {
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

        paymentService.pay(order);

        order.setStatus(OrderStatus.APPROVED);
        order.setApprovalDate(Calendar.getInstance());
        orderRepository.save(order);

        return new Response(200);
    }
}
