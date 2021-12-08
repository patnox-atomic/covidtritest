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
		return priorityService.getAllPriorities();
	}
	
	@GetMapping(path = "{priorityId}")
	public Priority getOrder(@PathVariable("priorityId") Long priorityId) {
		return priorityService.getPriority(priorityId);
	}
	
	@PostMapping
	public void createNewOrder(@RequestBody Priority newOrder)
	{
		priorityService.addNewPriority(newOrder);
	}
	
	@DeleteMapping(path = "{priorityId}")
	public void deleteOrder(@PathVariable("priorityId") Long priorityId)
	{
		priorityService.deletePriority(priorityId);
	}
	
	@PutMapping(path = "{priorityId}")
	public void updateOrder(@PathVariable("priorityId") Long priorityId,
				@RequestParam(required = false) Long product_id,
				@RequestParam(required = false) Long quantity,
				@RequestParam(required = false) Boolean is_fullfilled,
				@RequestParam(required = false) String date_ordered,
				@RequestParam(required = false) String date_fullfilled,
				@RequestParam(required = false) Boolean is_deleted
			)
	{
		priorityService.updatePriority(priorityId, product_id, quantity, is_fullfilled, date_ordered, date_fullfilled, is_deleted);
	}
	
}
