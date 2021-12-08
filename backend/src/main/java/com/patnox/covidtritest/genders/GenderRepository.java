package com.patnox.covidtritest.genders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long>
{
//	@Query("SELECT s FROM orders s WHERE s.product.id = ?1")
//	Optional<Gender> findOrderByProduct(long id);
//
//	@Query("SELECT s FROM orders s WHERE s.product.id = ?1 and is_fullfilled = false")
//	//Optional<Gender> findUnfulfilledOrderByProduct(long id);
//	List<Gender> findUnfulfilledOrderByProduct(long id);
}
