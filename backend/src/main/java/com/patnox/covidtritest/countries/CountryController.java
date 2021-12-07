package com.patnox.covidtritest.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/country")
public class CountryController
{

	private final CountryService countryService;

	@Autowired
	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping
	public List<Country> getAll() {
		return countryService.getAllOrders();
	}
	
	@GetMapping(path = "{orderId}")
	public Country getOrder(@PathVariable("orderId") Long orderId) {
		return countryService.getOrder(orderId);
	}
	
	@PostMapping
	public void createNewOrder(@RequestBody Country newOrder)
	{
		countryService.addNewOrder(newOrder);
	}
	
	@DeleteMapping(path = "{orderId}")
	public void deleteOrder(@PathVariable("orderId") Long orderId)
	{
		countryService.deleteOrder(orderId);
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
		countryService.updateOrder(orderId, product_id, quantity, is_fullfilled, date_ordered, date_fullfilled, is_deleted);
	}
	
}
