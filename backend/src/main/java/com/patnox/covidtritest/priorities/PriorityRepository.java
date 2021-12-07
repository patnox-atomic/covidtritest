package com.patnox.covidtritest.priorities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long>
{
	@Query("SELECT s FROM orders s WHERE s.product.id = ?1")
	Optional<Priority> findOrderByProduct(long id);
	
	@Query("SELECT s FROM orders s WHERE s.product.id = ?1 and is_fullfilled = false")
	//Optional<Priority> findUnfulfilledOrderByProduct(long id);
	List<Priority> findUnfulfilledOrderByProduct(long id);
}
