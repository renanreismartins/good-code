package refactoring.controller;

import refactoring.domain.Order;
import refactoring.domain.OrderItem;
import refactoring.domain.OrderStatus;
import refactoring.domain.Product;
import refactoring.repository.OrderRepository;
import refactoring.repository.ProductCatalog;

import java.util.ArrayList;

public class OrderCreationUseCase {
    private final OrderRepository orderRepository;
    private final ProductCatalog productCatalog;

    public OrderCreationUseCase(OrderRepository orderRepository, ProductCatalog productCatalog) {
        this.orderRepository = orderRepository;
        this.productCatalog = productCatalog;
    }

    public void run(SellItemsRequest request) {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setItems(new ArrayList<>());
        order.setCurrency("EUR");
        order.setTotal(0D);
        order.setTax(0D);

        for (SellItemRequest itemRequest : request.getRequests()) {
            Product product = productCatalog.getByName(itemRequest.getProductName());

            if (product == null) {
                throw new UnknownProductException();
            }
            else {
                final Double unitaryTax = (product.getPrice() / 100) * product.getCategory().getTaxPercentage();
                final Double unitaryTaxedAmount = product.getPrice() + unitaryTax;
                final Double taxedAmount = unitaryTaxedAmount  * itemRequest.getQuantity();
                final Double taxAmount = unitaryTax * itemRequest.getQuantity();

                final OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setTax(taxAmount);
                orderItem.setTaxedAmount(taxedAmount);
                order.getItems().add(orderItem);

                order.setTotal(order.getTotal() + taxedAmount);
                order.setTax(order.getTax()  + taxAmount);
            }
        }

        orderRepository.save(order);
    }
}
