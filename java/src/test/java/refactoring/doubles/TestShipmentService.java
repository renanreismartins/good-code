package refactoring.doubles;

import refactoring.domain.Order;
import refactoring.domain.OrderStatus;
import refactoring.service.ShipmentService;

import java.util.Calendar;

public class TestShipmentService implements ShipmentService {
    private Order shippedOrder = null;

    public Order getShippedOrder() {
        return shippedOrder;
    }

    @Override
    public Calendar calculateShipmentDate(Order order) {
        this.shippedOrder = order;

        // Same as the Mockito: when(order.getStatus).thenReturn(OrderStatus.SHIPPED)
        order.setStatus(OrderStatus.SHIPPED);
        return Calendar.getInstance();
    }
}
