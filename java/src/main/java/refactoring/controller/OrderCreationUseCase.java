package refactoring.controller;

import refactoring.domain.Order;
import refactoring.domain.OrderItem;
import refactoring.domain.OrderStatus;
import refactoring.domain.Product;
import refactoring.repository.OrderRepository;
import refactoring.repository.ProductCatalog;

import java.util.ArrayList;
import java.util.List;

public class OrderCreationUseCase {
    private final OrderRepository orderRepository;
    private final ProductCatalog productCatalog;

    public OrderCreationUseCase(OrderRepository orderRepository, ProductCatalog productCatalog) {
        this.orderRepository = orderRepository;
        this.productCatalog = productCatalog;
    }

    public void run(List<SellItemRequest> requests) {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setItems(new ArrayList<>());
        order.setCurrency("EUR");
        order.setTotal(0D);
        order.setTax(0D);

	    requests.forEach(r -> {
		    Product product = productCatalog.getById(r.getId());

		    if (product == null) {
			    throw new UnknownProductException();
		    } else {
			    final Double unitaryTax = (product.getPrice() / 100) * product.getCategory().getTaxPercentage();
			    final Double unitaryTaxedAmount = product.getPrice() + unitaryTax;
			    final Double taxedAmount = unitaryTaxedAmount * r.getQuantity();
			    final Double taxAmount = unitaryTax * r.getQuantity();

			    final OrderItem orderItem = new OrderItem();
			    orderItem.setProduct(product);
			    orderItem.setQuantity(r.getQuantity());
			    orderItem.setTax(taxAmount);
			    orderItem.setTaxedAmount(taxedAmount);
			    order.getItems().add(orderItem);

			    order.setTotal(order.getTotal() + taxedAmount);
			    order.setTax(order.getTax() + taxAmount);
		    }

		    orderRepository.save(order);
	    });
    }
}
