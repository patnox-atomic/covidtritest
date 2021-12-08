package com.patnox.covidtritest.patients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PatientService
{
	private final PatientRepository patientRepository;

	  @Autowired
	  public PatientService(PatientRepository patientRepository) {
	    this.patientRepository = patientRepository;
	  }
	  
	public List<Patient> getAllPatients()
	{
	    return patientRepository.findAll();
	}
	
	//Get a specific patient
	public Patient getPatient(Long patientId)
	{
	    return patientRepository.findById(patientId).orElseThrow(() -> new IllegalStateException("Patient with ID: " + patientId + " does not exist"));
	}
	
	public void addNewPatient(Patient newPatient)
	{
		System.out.println("My New Patient: " + newPatient);
		patientRepository.save(newPatient);
	}
	
	@Transactional
	public void deletePatient(Long patientId)
	{
		System.out.println("Request to delete Patient ID: " + patientId);
		boolean exists = patientRepository.existsById(patientId);
		if(!exists)
		{
			System.err.println("Error: Patient with ID: " + patientId + " does not exist");
			throw new IllegalStateException("Patient with ID: " + patientId + " does not exist");
		}
		else
		{
			System.out.println("Patient with ID: " + patientId + " exists so we will proceed");
			Patient victimizedOrder = patientRepository.findById(patientId).orElseThrow(() -> new IllegalStateException("Patient with ID: " + patientId + " does not exist"));
			victimizedOrder.setIs_deleted(true);
		}
	}
	
	@Transactional
	public void updatePatient(Long patientId, Long product_id, Long quantity, Boolean is_fullfilled, String date_ordered, String date_fullfilled, Boolean is_deleted)
	{
		System.out.println("Request to update Patient ID: " + patientId);
		boolean exists = patientRepository.existsById(patientId);
		if(!exists)
		{
			System.err.println("Error: Patient with ID: " + patientId + " does not exist");
			throw new IllegalStateException("Patient with ID: " + patientId + " does not exist");
		}
		else
		{
			System.out.println("Patient with ID: " + patientId + " exists so we will proceed");
			Patient victimizedOrder = patientRepository.findById(patientId).orElseThrow(() -> new IllegalStateException("Patient with ID: " + patientId + " does not exist"));
			//Product victimizedProduct = productRepository.findById(product_id).orElseThrow(() -> new IllegalStateException("Product with ID: " + product_id + " does not exist"));
			//if(product_id != null && product_id != 0) victimizedOrder.setProduct(victimizedProduct);
//			if(quantity != null && quantity != 0) victimizedOrder.setQuantity(quantity);
//			if(is_fullfilled != null) victimizedOrder.setIs_fullfilled(is_fullfilled);
//			if(product_id != null && date_ordered.length() > 0) victimizedOrder.setDate_ordered(LocalDate.parse(date_ordered, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//			if(date_fullfilled != null && date_fullfilled.length() > 0) victimizedOrder.setDate_fullfilled(LocalDate.parse(date_fullfilled, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			if(is_deleted != null) victimizedOrder.setIs_deleted(is_deleted);
		}
	}
}
