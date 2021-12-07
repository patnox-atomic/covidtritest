package com.patnox.covidtritest.patients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/order")
public class PatientController {

	private final PatientService patientService;

	@Autowired
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping
	public List<Patient> getAll() {
		return patientService.getAllOrders();
	}
	
	@GetMapping(path = "{orderId}")
	public Patient getOrder(@PathVariable("orderId") Long orderId) {
		return patientService.getOrder(orderId);
	}
	
	@PostMapping
	public void createNewOrder(@RequestBody Patient newOrder)
	{
		patientService.addNewOrder(newOrder);
	}
	
	@PostMapping("/fullfill")
	public void fullfillOrder(@RequestParam(required = true) Long orderId)
	{
		patientService.fullfillOrder(orderId);
	}
	
	@DeleteMapping(path = "{orderId}")
	public void deleteOrder(@PathVariable("orderId") Long orderId)
	{
		patientService.deleteOrder(orderId);
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
		patientService.updateOrder(orderId, product_id, quantity, is_fullfilled, date_ordered, date_fullfilled, is_deleted);
	}
	
}
