package com.patnox.covidtritest.patients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/patient")
public class PatientController {

	private final PatientService patientService;

	@Autowired
	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@GetMapping
	public List<Patient> getAll() {
		return patientService.getAllPatients();
	}
	
	@GetMapping(path = "{patientId}")
	public Patient getPatient(@PathVariable("patientId") Long patientId) {
		return patientService.getPatient(patientId);
	}
	
	@PostMapping
	public void createNewPatient(@RequestBody Patient newPatient)
	{
		patientService.addNewPatient(newPatient);
	}
	
	@DeleteMapping(path = "{patientId}")
	public void deletePatient(@PathVariable("patientId") Long patientId)
	{
		patientService.deletePatient(patientId);
	}
	
	@PutMapping(path = "{patientId}")
	public void updatePatient(@PathVariable("patientId") Long patientId,
				@RequestParam(required = false) Long product_id,
				@RequestParam(required = false) Long quantity,
				@RequestParam(required = false) Boolean is_fullfilled,
				@RequestParam(required = false) String date_ordered,
				@RequestParam(required = false) String date_fullfilled,
				@RequestParam(required = false) Boolean is_deleted
			)
	{
		patientService.updatePatient(patientId, product_id, quantity, is_fullfilled, date_ordered, date_fullfilled, is_deleted);
	}
	
}
