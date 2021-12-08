package com.patnox.covidtritest.genders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GenderService
{
	private final GenderRepository genderRepository;

	  @Autowired
	  public GenderService(GenderRepository genderRepository) {
	    this.genderRepository = genderRepository;
	  }
	  
	public List<Gender> getAllGenders()
	{
	    return genderRepository.findAll();
	}
	
	//Get a specific gender
	public Gender getGender(Long genderId)
	{
	    return genderRepository.findById(genderId).orElseThrow(() -> new IllegalStateException("Gender with ID: " + genderId + " does not exist"));
	}
	
	public void addNewGender(Gender newGender)
	{
		System.out.println("My New Gender: " + newGender);
		genderRepository.save(newGender);
	}
	
	@Transactional
	public void deleteGender(Long genderId)
	{
		System.out.println("Request to delete Gender ID: " + genderId);
		boolean exists = genderRepository.existsById(genderId);
		if(!exists)
		{
			System.err.println("Error: Gender with ID: " + genderId + " does not exist");
			throw new IllegalStateException("Gender with ID: " + genderId + " does not exist");
		}
		else
		{
			System.out.println("Gender with ID: " + genderId + " exists so we will proceed");
			Gender victimizedOrder = genderRepository.findById(genderId).orElseThrow(() -> new IllegalStateException("Gender with ID: " + genderId + " does not exist"));
			victimizedOrder.setIs_deleted(true);
		}
	}
	
	@Transactional
	public void updateGender(Long genderId, Long product_id, Long quantity, Boolean is_fullfilled, String date_ordered, String date_fullfilled, Boolean is_deleted)
	{
		System.out.println("Request to update Gender ID: " + genderId);
		boolean exists = genderRepository.existsById(genderId);
		if(!exists)
		{
			System.err.println("Error: Gender with ID: " + genderId + " does not exist");
			throw new IllegalStateException("Gender with ID: " + genderId + " does not exist");
		}
		else
		{
			System.out.println("Gender with ID: " + genderId + " exists so we will proceed");
			Gender victimizedOrder = genderRepository.findById(genderId).orElseThrow(() -> new IllegalStateException("Gender with ID: " + genderId + " does not exist"));
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
