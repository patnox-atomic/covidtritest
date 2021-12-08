package com.patnox.covidtritest.triage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TriageService
{
	private final TriageRepository triageRepository;

	  @Autowired
	  public TriageService(TriageRepository triageRepository) {
	    this.triageRepository = triageRepository;
	  }
	  
	public List<Triage> getAllTriages()
	{
	    return triageRepository.findAll();
	}

	public List<Triage> getQueue()
	{
		return triageRepository.getTriageQueue();
	}
	
	//Get a specific triage
	public Triage getTriage(Long triageId)
	{
	    return triageRepository.findById(triageId).orElseThrow(() -> new IllegalStateException("Triage with ID: " + triageId + " does not exist"));
	}

	@Transactional
	public void treatPatient(Long triageId)
	{
		boolean triageExists = triageRepository.existsById(triageId);
		if(!triageExists)
		{
			System.err.println("Error: Triage with ID: " + triageId + " does not exist");
			throw new IllegalStateException("Triage with ID: " + triageId + " does not exist");
		}
		else
		{
			System.out.println("Triage with ID: " + triageId + " exists so we will proceed");
			//search for the Triage
			Triage selectedTriage = triageRepository.findById(triageId).orElseThrow(() -> new IllegalStateException("Order with ID: " + triageId + " does not exist"));
			//check if triage has already been treated
			if(!selectedTriage.getIs_treated())
			{
				//mark Triage as treated
				selectedTriage.setIs_treated(true);
			}
			else
			{
				System.err.println("Error: Triage with ID: " + triageId + " is already treated");
				throw new IllegalStateException("Order with ID: " + triageId + " is already treated");
			}
		}
	}
	
	public void addNewTriage(Triage newTriage)
	{
		System.out.println("My New Triage: " + newTriage);
		triageRepository.save(newTriage);
	}
	
	@Transactional
	public void deleteTriage(Long triageId)
	{
		System.out.println("Request to delete Triage ID: " + triageId);
		boolean exists = triageRepository.existsById(triageId);
		if(!exists)
		{
			System.err.println("Error: Triage with ID: " + triageId + " does not exist");
			throw new IllegalStateException("Triage with ID: " + triageId + " does not exist");
		}
		else
		{
			System.out.println("Triage with ID: " + triageId + " exists so we will proceed");
			Triage victimizedOrder = triageRepository.findById(triageId).orElseThrow(() -> new IllegalStateException("Triage with ID: " + triageId + " does not exist"));
			victimizedOrder.setIs_deleted(true);
		}
	}
	
	@Transactional
	public void updateTriage(Long triageId, Long product_id, Long quantity, Boolean is_fullfilled, String date_ordered, String date_fullfilled, Boolean is_deleted)
	{
		System.out.println("Request to update Triage ID: " + triageId);
		boolean exists = triageRepository.existsById(triageId);
		if(!exists)
		{
			System.err.println("Error: Triage with ID: " + triageId + " does not exist");
			throw new IllegalStateException("Triage with ID: " + triageId + " does not exist");
		}
		else
		{
			System.out.println("Triage with ID: " + triageId + " exists so we will proceed");
			Triage victimizedOrder = triageRepository.findById(triageId).orElseThrow(() -> new IllegalStateException("Triage with ID: " + triageId + " does not exist"));
//			Product victimizedProduct = productRepository.findById(product_id).orElseThrow(() -> new IllegalStateException("Product with ID: " + product_id + " does not exist"));
//			if(product_id != null && product_id != 0) victimizedOrder.setProduct(victimizedProduct);
//			if(quantity != null && quantity != 0) victimizedOrder.setQuantity(quantity);
//			if(is_fullfilled != null) victimizedOrder.setIs_fullfilled(is_fullfilled);
//			if(product_id != null && date_ordered.length() > 0) victimizedOrder.setDate_ordered(LocalDate.parse(date_ordered, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//			if(date_fullfilled != null && date_fullfilled.length() > 0) victimizedOrder.setDate_fullfilled(LocalDate.parse(date_fullfilled, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			if(is_deleted != null) victimizedOrder.setIs_deleted(is_deleted);
		}
	}
}
