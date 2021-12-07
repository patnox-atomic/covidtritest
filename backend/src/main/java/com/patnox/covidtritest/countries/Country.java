package com.patnox.covidtritest.countries;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@DynamicInsert
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "countries")
@Table(name = "countries")
public class Country
{
	  @Id
	  @Column(name = "id", unique = true, nullable = false)
	  @SequenceGenerator(
		  name = "countries_sequence",
		  sequenceName = "countries_sequence",
		  allocationSize = 1
	  )
	  @GeneratedValue(
			  strategy = GenerationType.SEQUENCE,
			  generator = "countries_sequence"
	  )
	  private Long id;

	  @NotBlank
	  @NotNull
	  @Column(name = "name", length = 254)
	  @Size(min = 1, max = 254)
	  @Length(min = 1, max = 254)
	  private String name;

	  @Column(name = "is_deleted" ,columnDefinition = "boolean default false")
	  private Boolean is_deleted;

	  public Country() {}

	public Country(Long id, String name, Boolean is_deleted) {
		this.id = id;
		this.name = name;
		this.is_deleted = is_deleted;
	}

	public Country(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	@Override
	public String toString() {
		return "Country{" +
				"id=" + id +
				", name='" + name + '\'' +
				", is_deleted=" + is_deleted +
				'}';
	}

	//	@Override
//	public String toString() {
//		return "Order [id=" + id + ", quantity=" + quantity + ", is_fullfilled=" + is_fullfilled + ", date_ordered="
//				+ date_ordered + ", date_fullfilled=" + date_fullfilled + ", is_deleted=" + is_deleted
//				+ ", product_id=" + getProduct_id() + "]";
//	}

//	public String toString() 
//	{
//		   return ToStringBuilder.reflectionToString(this);
//	}
		
}
