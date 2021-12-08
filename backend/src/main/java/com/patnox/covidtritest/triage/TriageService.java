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
	  
	public List<Triage> getAllOrders()
	{
	    return triageRepository.findAll();
	}
	
	//Get a specific order
	public Triage getOrder(Long orderId) 
	{
	    return triageRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("Triage with ID: " + orderId + " does not exist"));
	}
	
	public void addNewOrder(Triage newOrder)
	{
		System.out.println("My New Triage: " + newOrder);
		triageRepository.save(newOrder);
	}
	
	@Transactional
	public void deleteOrder(Long orderId)
	{
		System.out.println("Request to delete Triage ID: " + orderId);
		boolean exists = triageRepository.existsById(orderId);
		if(!exists)
		{
			System.err.println("Error: Triage with ID: " + orderId + " does not exist");
			throw new IllegalStateException("Triage with ID: " + orderId + " does not exist");
		}
		else
		{
			System.out.println("Triage with ID: " + orderId + " exists so we will proceed");
			Triage victimizedOrder = triageRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("Triage with ID: " + orderId + " does not exist"));
			victimizedOrder.setIs_deleted(true);
		}
	}
	
	@Transactional
	public void updateOrder(Long orderId, Long product_id, Long quantity, Boolean is_fullfilled, String date_ordered, String date_fullfilled, Boolean is_deleted)
	{
		System.out.println("Request to update Triage ID: " + orderId);
		boolean exists = triageRepository.existsById(orderId);
		if(!exists)
		{
			System.err.println("Error: Triage with ID: " + orderId + " does not exist");
			throw new IllegalStateException("Triage with ID: " + orderId + " does not exist");
		}
		else
		{
			System.out.println("Triage with ID: " + orderId + " exists so we will proceed");
			Triage victimizedOrder = triageRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("Triage with ID: " + orderId + " does not exist"));
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
