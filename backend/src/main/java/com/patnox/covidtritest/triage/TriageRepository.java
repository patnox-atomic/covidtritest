package com.patnox.covidtritest.triage;

import com.patnox.covidtritest.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TriageRepository extends JpaRepository<Order, Long>
{
	@Query("SELECT s FROM orders s WHERE s.product.id = ?1")
	Optional<Order> findOrderByProduct(long id);
	
	@Query("SELECT s FROM orders s WHERE s.product.id = ?1 and is_fullfilled = false")
	//Optional<Order> findUnfulfilledOrderByProduct(long id);
	List<Order> findUnfulfilledOrderByProduct(long id);
}
