package com.patnox.covidtritest.priorities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/priority")
public class PriorityController 
{

	private final PriorityService priorityService;

	@Autowired
	public PriorityController(PriorityService priorityService) {
		this.priorityService = priorityService;
	}

	@GetMapping
	public List<Priority> getAll() {
		return priorityService.getAllOrders();
	}
	
	@GetMapping(path = "{orderId}")
	public Priority getOrder(@PathVariable("orderId") Long orderId) {
		return priorityService.getOrder(orderId);
	}
	
	@PostMapping
	public void createNewOrder(@RequestBody Priority newOrder)
	{
		priorityService.addNewOrder(newOrder);
	}
	
	@DeleteMapping(path = "{orderId}")
	public void deleteOrder(@PathVariable("orderId") Long orderId)
	{
		priorityService.deleteOrder(orderId);
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
		priorityService.updateOrder(orderId, product_id, quantity, is_fullfilled, date_ordered, date_fullfilled, is_deleted);
	}
	
}
