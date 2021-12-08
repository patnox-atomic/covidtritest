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
		return genderService.getAllGenders();
	}
	
	@GetMapping(path = "{genderId}")
	public Gender getOrder(@PathVariable("genderId") Long genderId) {
		return genderService.getGender(genderId);
	}
	
	@PostMapping
	public void createNewOrder(@RequestBody Gender newOrder)
	{
		genderService.addNewGender(newOrder);
	}
	
	@DeleteMapping(path = "{genderId}")
	public void deleteOrder(@PathVariable("genderId") Long genderId)
	{
		genderService.deleteGender(genderId);
	}
	
	@PutMapping(path = "{genderId}")
	public void updateOrder(@PathVariable("genderId") Long genderId,
				@RequestParam(required = false) Long product_id,
				@RequestParam(required = false) Long quantity,
				@RequestParam(required = false) Boolean is_fullfilled,
				@RequestParam(required = false) String date_ordered,
				@RequestParam(required = false) String date_fullfilled,
				@RequestParam(required = false) Boolean is_deleted
			)
	{
		genderService.updateGender(genderId, product_id, quantity, is_fullfilled, date_ordered, date_fullfilled, is_deleted);
	}
	
}
