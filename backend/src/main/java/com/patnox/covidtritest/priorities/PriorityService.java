package com.patnox.covidtritest.priorities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PriorityService
{
	private final PriorityRepository priorityRepository;

	  @Autowired
	  public PriorityService(PriorityRepository priorityRepository) {
	    this.priorityRepository = priorityRepository;
	  }
	  
	public List<Priority> getAllPriorities()
	{
	    return priorityRepository.findAll();
	}
	
	//Get a specific priority
	public Priority getPriority(Long priorityId)
	{
	    return priorityRepository.findById(priorityId).orElseThrow(() -> new IllegalStateException("Priority with ID: " + priorityId + " does not exist"));
	}
	
	public void addNewPriority(Priority newPriority)
	{
		System.out.println("My New Priority: " + newPriority);
		priorityRepository.save(newPriority);
	}
	
	@Transactional
	public void deletePriority(Long priorityId)
	{
		System.out.println("Request to delete Priority ID: " + priorityId);
		boolean exists = priorityRepository.existsById(priorityId);
		if(!exists)
		{
			System.err.println("Error: Priority with ID: " + priorityId + " does not exist");
			throw new IllegalStateException("Priority with ID: " + priorityId + " does not exist");
		}
		else
		{
			System.out.println("Priority with ID: " + priorityId + " exists so we will proceed");
			Priority victimizedOrder = priorityRepository.findById(priorityId).orElseThrow(() -> new IllegalStateException("Priority with ID: " + priorityId + " does not exist"));
			victimizedOrder.setIs_deleted(true);
		}
	}
	
	@Transactional
	public void updatePriority(Long priorityId, Long product_id, Long quantity, Boolean is_fullfilled, String date_ordered, String date_fullfilled, Boolean is_deleted)
	{
		System.out.println("Request to update Priority ID: " + priorityId);
		boolean exists = priorityRepository.existsById(priorityId);
		if(!exists)
		{
			System.err.println("Error: Priority with ID: " + priorityId + " does not exist");
			throw new IllegalStateException("Priority with ID: " + priorityId + " does not exist");
		}
		else
		{
			System.out.println("Priority with ID: " + priorityId + " exists so we will proceed");
			Priority victimizedOrder = priorityRepository.findById(priorityId).orElseThrow(() -> new IllegalStateException("Priority with ID: " + priorityId + " does not exist"));
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
