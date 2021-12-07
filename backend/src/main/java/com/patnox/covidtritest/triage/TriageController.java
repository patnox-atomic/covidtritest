package com.patnox.covidtritest.triage;

import com.patnox.covidtritest.orders.Order;
import com.patnox.covidtritest.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/order")
public class TriageController {

	private final OrderService orderService;

	@Autowired
	public TriageController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping
	public List<Order> getAll() {
		return orderService.getAllOrders();
	}
	
	@GetMapping(path = "{orderId}")
	public Order getOrder(@PathVariable("orderId") Long orderId) {
		return orderService.getOrder(orderId);
	}
	
	@PostMapping
	public void createNewOrder(@RequestBody Order newOrder)
	{
		orderService.addNewOrder(newOrder);
	}
	
	@PostMapping("/fullfill")
	public void fullfillOrder(@RequestParam(required = true) Long orderId)
	{
		orderService.fullfillOrder(orderId);
	}
	
	@DeleteMapping(path = "{orderId}")
	public void deleteOrder(@PathVariable("orderId") Long orderId)
	{
		orderService.deleteOrder(orderId);
	}
	
	@PutMapping(path = "{orderId}")
	public void updateOrder(@PathVariable("orderId") Long orderId,
				@RequestParam(required = false) Long product_id,
				@RequestParam(required = false) Long quantity,
				@RequestParam(required = false) Boolean is_fullfilled,
				@RequestParam(required = false) String date_ordered,
				@RequestParam(required = false) String date_fullfilled,
				@RequestParam(required = false) Boolean is_deleted
			)
	{
		orderService.updateOrder(orderId, product_id, quantity, is_fullfilled, date_ordered, date_fullfilled, is_deleted);
	}
	
}
