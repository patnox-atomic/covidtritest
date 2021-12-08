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
		return triageService.getAllTriages();
	}

	@GetMapping("/queue")
	public List<Triage> getQueue() {
		return triageService.getQueue();
	}
	
	@GetMapping(path = "{triageId}")
	public Triage getTriage(@PathVariable("triageId") Long triageId) {
		return triageService.getTriage(triageId);
	}

	@PostMapping("/treat")
	public void treatPatient(@RequestParam(required = true) Long triageId)
	{
		triageService.treatPatient(triageId);
	}
	
	@PostMapping
	public void createNewTriage(@RequestBody Triage newTriage)
	{
		triageService.addNewTriage(newTriage);
	}
	
	@DeleteMapping(path = "{triageId}")
	public void deleteTriage(@PathVariable("triageId") Long triageId)
	{
		triageService.deleteTriage(triageId);
	}
	
	@PutMapping(path = "{triageId}")
	public void updateTriage(@PathVariable("triageId") Long triageId,
				@RequestParam(required = false) Long product_id,
				@RequestParam(required = false) Long quantity,
				@RequestParam(required = false) Boolean is_fullfilled,
				@RequestParam(required = false) String date_ordered,
				@RequestParam(required = false) String date_fullfilled,
				@RequestParam(required = false) Boolean is_deleted
			)
	{
		triageService.updateTriage(triageId, product_id, quantity, is_fullfilled, date_ordered, date_fullfilled, is_deleted);
	}
	
}
