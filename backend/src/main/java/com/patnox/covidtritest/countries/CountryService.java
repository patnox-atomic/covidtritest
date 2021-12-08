package com.patnox.covidtritest.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CountryService
{
	private final CountryRepository countryRepository;

	  @Autowired
	  public CountryService(CountryRepository countryRepository) {
	    this.countryRepository = countryRepository;
	  }
	  
	public List<Country> getAllCountries()
	{
	    return countryRepository.findAll();
	}
	
	//Get a specific Country
	public Country getCountry(Long countryId)
	{
	    return countryRepository.findById(countryId).orElseThrow(() -> new IllegalStateException("Country with ID: " + countryId + " does not exist"));
	}
	
	public void addNewCountry(Country newCountry)
	{
		System.out.println("My New Country: " + newCountry);
		countryRepository.save(newCountry);
	}
	
	@Transactional
	public void deleteCountry(Long countryId)
	{
		System.out.println("Request to delete Country ID: " + countryId);
		boolean exists = countryRepository.existsById(countryId);
		if(!exists)
		{
			System.err.println("Error: Country with ID: " + countryId + " does not exist");
			throw new IllegalStateException("Country with ID: " + countryId + " does not exist");
		}
		else
		{
			System.out.println("Country with ID: " + countryId + " exists so we will proceed");
			Country victimizedOrder = countryRepository.findById(countryId).orElseThrow(() -> new IllegalStateException("Country with ID: " + countryId + " does not exist"));
			victimizedOrder.setIs_deleted(true);
		}
	}
	
	@Transactional
	public void updateCountry(Long countryId, Long product_id, Long quantity, Boolean is_fullfilled, String date_ordered, String date_fullfilled, Boolean is_deleted)
	{
		System.out.println("Request to update Country ID: " + countryId);
		boolean exists = countryRepository.existsById(countryId);
		if(!exists)
		{
			System.err.println("Error: Country with ID: " + countryId + " does not exist");
			throw new IllegalStateException("Country with ID: " + countryId + " does not exist");
		}
		else
		{
			System.out.println("Country with ID: " + countryId + " exists so we will proceed");
			Country victimizedOrder = countryRepository.findById(countryId).orElseThrow(() -> new IllegalStateException("Country with ID: " + countryId + " does not exist"));
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
