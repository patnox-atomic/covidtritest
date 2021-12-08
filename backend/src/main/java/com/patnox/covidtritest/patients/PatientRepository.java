package com.patnox.covidtritest.patients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>
{
//	@Query("SELECT s FROM patients s WHERE s.country.id = ?1")
//	Optional<Patient> findPatientByCountry(long id);
	
	//@Query("SELECT s FROM orders s WHERE s.product.id = ?1 and is_fullfilled = false")
	////Optional<Patient> findUnfulfilledOrderByProduct(long id);
	//List<Patient> findUnfulfilledOrderByProduct(long id);
}
