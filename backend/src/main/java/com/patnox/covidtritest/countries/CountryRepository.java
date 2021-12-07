package com.patnox.covidtritest.countries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>
{
	@Query("SELECT s FROM orders s WHERE s.product.id = ?1")
	Optional<Country> findOrderByProduct(long id);
	
	@Query("SELECT s FROM orders s WHERE s.product.id = ?1 and is_fullfilled = false")
	//Optional<Order> findUnfulfilledOrderByProduct(long id);
	List<Country> findUnfulfilledOrderByProduct(long id);
}
