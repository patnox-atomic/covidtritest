package com.patnox.covidtritest.triage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/triage")
public class TriageController {

	private final TriageService triageService;

	@Autowired
	public TriageController(TriageService triageService) {
		this.triageService = triageService;
	}

	@GetMapping
	public List<Triage> getAll() {
		return triageService.getAllOrders();
	}
	
	@GetMapping(path = "{orderId}")
	public Triage getOrder(@PathVariable("orderId") Long orderId) {
		return triageService.getOrder(orderId);
	}
	
	@PostMapping
	public void createNewOrder(@RequestBody Triage newOrder)
	{
		triageService.addNewOrder(newOrder);
	}
	
	@DeleteMapping(path = "{orderId}")
	public void deleteOrder(@PathVariable("orderId") Long orderId)
	{
		triageService.deleteOrder(orderId);
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
		triageService.updateOrder(orderId, product_id, quantity, is_fullfilled, date_ordered, date_fullfilled, is_deleted);
	}
	
}
