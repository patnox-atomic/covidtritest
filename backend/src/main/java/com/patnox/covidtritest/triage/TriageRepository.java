package com.patnox.covidtritest.triage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TriageRepository extends JpaRepository<Triage, Long>
{
//	@Query("SELECT s FROM orders s WHERE s.product.id = ?1")
//	Optional<Triage> findOrderByProduct(long id);
//
//	@Query("SELECT s FROM orders s WHERE s.product.id = ?1 and is_fullfilled = false")
//	//Optional<Triage> findUnfulfilledOrderByProduct(long id);
//	List<Triage> findUnfulfilledOrderByProduct(long id);
    @Query("SELECT s FROM triage s where is_treated = false Order by priority.rank asc, id asc")
	List<Triage> getTriageQueue();
}
