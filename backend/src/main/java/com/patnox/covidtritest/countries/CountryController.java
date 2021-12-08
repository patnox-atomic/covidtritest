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
		return countryService.getAllCountries();
	}
	
	@GetMapping(path = "{countryId}")
	public Country getOrder(@PathVariable("countryId") Long countryId) {
		return countryService.getCountry(countryId);
	}
	
	@PostMapping
	public void createNewOrder(@RequestBody Country newOrder)
	{
		countryService.addNewCountry(newOrder);
	}
	
	@DeleteMapping(path = "{countryId}")
	public void deleteOrder(@PathVariable("countryId") Long countryId)
	{
		countryService.deleteCountry(countryId);
	}
	
	@PutMapping(path = "{countryId}")
	public void updateOrder(@PathVariable("countryId") Long countryId,
				@RequestParam(required = false) Long product_id,
				@RequestParam(required = false) Long quantity,
				@RequestParam(required = false) Boolean is_fullfilled,
				@RequestParam(required = false) String date_ordered,
				@RequestParam(required = false) String date_fullfilled,
				@RequestParam(required = false) Boolean is_deleted
			)
	{
		countryService.updateCountry(countryId, product_id, quantity, is_fullfilled, date_ordered, date_fullfilled, is_deleted);
	}
	
}
