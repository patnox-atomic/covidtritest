package com.patnox.covidtritest;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import java.time.*;
import java.util.*;
import com.patnox.covidtritest.countries.*;
import com.patnox.covidtritest.genders.*;
import com.patnox.covidtritest.patients.*;
import com.patnox.covidtritest.priorities.*;
import com.patnox.covidtritest.triage.*;
import com.patnox.covidtritest.security.*;

@Configuration
public class DataConfig 
{
	@Bean
    public JavaMailSender javaMailSender() { 
          //return new JavaMailSender();
          return new JavaMailSenderImpl();
    }
	
	@Bean
	CommandLineRunner productDataInjector(CountryRepository countryRepository, GenderRepository genderRepository,
										  PatientRepository patientRepository, PriorityRepository priorityRepository,
										  TriageRepository triageRepository,
			AppUserService userService, AppUserRoleRepository appUserRoleRepository)
	{
		return args -> {
			//Sample countries
			Country count1 = new Country(
					"Kenya"
			);
			Country count2 = new Country(
					"Uganda"
			);
			Country count3 = new Country(
					"Tanzania"
			);
			Country count4 = new Country(
					"Ethiopia"
			);
			Country count5 = new Country(
					"Somalia"
			);
			Country count6 = new Country(
					"Rwanda"
			);
			countryRepository.saveAll(
					List.of(count1, count2, count3, count4, count5, count6)
			);
			//Sample Genders
			Gender gen1 = new Gender(
					"Male"
			);
			Gender gen2 = new Gender(
					"Female"
			);
			genderRepository.saveAll(
					List.of(gen1, gen2)
			);
			//Sample Priorities
			Priority pri1 = new Priority(
					1L,
					"Priority One",
					"Critical Condition"
			);
			Priority pri2 = new Priority(
					2L,
					"Priority Two",
					"Emergency Condition"
			);
			Priority pri3 = new Priority(
					3L,
					"Priority Three",
					"GENERAL PRACTICE or AH SERVICE"
			);
			Priority pri4 = new Priority(
					3L,
					"Priority Four",
					"DISCUSS WITH GP"
			);
			priorityRepository.saveAll(
					List.of(pri1, pri2, pri3, pri4)
			);
			//Sample patients
			Patient pat1 = new Patient(
					count1,
					gen1,
					"John",
					"Mwangi",
					"P.O Box 123456 Nairobi Kenya",
					"john.mwangi@gmail.com",
					"0722374627",
					LocalDate.of(2001, Month.DECEMBER, 15)
			);
			Patient pat2 = new Patient(
					count4,
					gen1,
					"Sonko",
					"Mbuvi",
					"P.O Box 123456 Addis Ababa Ethiopia",
					"sonko.mbuvi@gmail.com",
					"0776384992",
					LocalDate.of(1986, Month.NOVEMBER, 26)
			);
			Patient pat3 = new Patient(
					count5,
					gen1,
					"Moha",
					"Pevu",
					"P.O Box 123456 Mogadishu Somalia",
					"moha.pevu@gmail.com",
					"0745736882",
					LocalDate.of(1990, Month.FEBRUARY, 22)
			);
			Patient pat4 = new Patient(
					count3,
					gen1,
					"Agnes",
					"Wangui",
					"P.O Box 389921 Dar Tanzania",
					"agnes.wangui@gmail.com",
					"0709738234",
					LocalDate.of(1992, Month.JULY, 4)
			);
			Patient pat5 = new Patient(
					count6,
					gen1,
					"Esther",
					"Wanjiru",
					"P.O Box 72881 Kigari Rwanda",
					"esther.wanjiru@gmail.com",
					"0738746332",
					LocalDate.of(1996, Month.AUGUST, 25)
			);
			Patient pat6 = new Patient(
					count2,
					gen1,
					"Jack",
					"Koigi",
					"P.O Box 837473 Kampala Uganda",
					"jack.koigi@gmail.com",
					"0711837237",
					LocalDate.of(2005, Month.FEBRUARY, 10)
			);
			patientRepository.saveAll(
					List.of(pat1, pat2, pat3, pat4, pat5, pat6)
			);
			//Sample roles
//			USER_ROLE,
//		    ADMIN_ROLE,
//		    MANAGER_ROLE,
//		    SUPERUSER_ROLE,
//		    MAINTENANCE_ROLE,
//		    AUDITOR_ROLE,
//		    WAREHOUSE_ATTENDANT_ROLE,
//		    RETAIL_ATTENDANT_ROLE
			AppUserRole aur1 = new AppUserRole(
					"USER_ROLE"
			);
			AppUserRole aur2 = new AppUserRole(
					"ADMIN_ROLE"
			);
			AppUserRole aur3 = new AppUserRole(
					"MANAGER_ROLE"
			);
			AppUserRole aur4 = new AppUserRole(
					"SUPERUSER_ROLE"
			);
			AppUserRole aur5 = new AppUserRole(
					"MAINTENANCE_ROLE"
			);
			AppUserRole aur6 = new AppUserRole(
					"AUDITOR_ROLE"
			);
			AppUserRole aur7 = new AppUserRole(
					"WAREHOUSE_ATTENDANT_ROLE"
			);
			AppUserRole aur8 = new AppUserRole(
					"RETAIL_ATTENDANT_ROLE"
			);
			appUserRoleRepository.saveAll(
					List.of(aur1, aur2, aur3, aur4, aur5, aur6, aur7, aur8)
			);
			//Sample users
			ArrayList<AppUserRole> role1 = new ArrayList<>();
			role1.add(aur8);
			ArrayList<AppUserRole> role2 = new ArrayList<>();
			role2.add(aur7);
			ArrayList<AppUserRole> role3 = new ArrayList<>();
			role3.add(aur4);
			role3.add(aur7);
			role3.add(aur8);
			AppUser au1 = new AppUser(
					"john",
					"doe",
					"john.doe@yahoo.com",
					"123456",
					role1,
					false,
					true
			);
			AppUser au2 = new AppUser(
					"peter",
					"mash",
					"peter.mash@gmail.com",
					"123456",
					role2,
					false,
					true
			);
			AppUser au3 = new AppUser(
					"Elon",
					"MASKER",
					"elon.masker@mailinator.com",
					"123456",
					role3,
					false,
					true
			);
			userService.signUpUser(au1);
			userService.signUpUser(au2);
			userService.signUpUser(au3);
		};
	}
	
}
