package com.patnox.covidtritest.genders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/gender")
public class GenderController
{

	private final GenderService genderService;

	@Autowired
	public GenderController(GenderService genderService) {
		this.genderService = genderService;
	}

	@GetMapping
	public List<Gender> getAll() {
		return genderService.getAllOrders();
	}
	
	@GetMapping(path = "{orderId}")
	public Gender getOrder(@PathVariable("orderId") Long orderId) {
		return genderService.getOrder(orderId);
	}
	
	@PostMapping
	public void createNewOrder(@RequestBody Gender newOrder)
	{
		genderService.addNewOrder(newOrder);
	}
	
	@DeleteMapping(path = "{orderId}")
	public void deleteOrder(@PathVariable("orderId") Long orderId)
	{
		genderService.deleteOrder(orderId);
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
		genderService.updateOrder(orderId, product_id, quantity, is_fullfilled, date_ordered, date_fullfilled, is_deleted);
	}
	
}
