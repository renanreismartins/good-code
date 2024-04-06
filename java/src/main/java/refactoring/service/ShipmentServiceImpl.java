package refactoring.service;

import refactoring.domain.Order;
import refactoring.domain.OrderStatus;

import java.util.Calendar;

/**
 * If the order is approved on Saturday, it takes 3 days to ship, if Sunday 2 days
 * and 1 day if approved on weekdays.
 *
 */
public class ShipmentServiceImpl implements ShipmentService {
	@Override
	public Calendar calculateShipmentDate(Order order) {

		Calendar shipmentDate = order.getApprovalDate();

		if (shipmentDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			shipmentDate.add(Calendar.DATE, 3);
		} else if (shipmentDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			shipmentDate.add(Calendar.DATE, 2);
		} else {
			shipmentDate.add(Calendar.DATE, 1);
		}

		order.setStatus(OrderStatus.SHIPPED);

		return shipmentDate;
	}
}
