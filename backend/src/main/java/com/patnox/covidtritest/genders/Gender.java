package com.patnox.covidtritest.genders;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@DynamicInsert
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "genders")
@Table(name = "genders")
public class Gender
{
	  @Id
	  @Column(name = "id", unique = true, nullable = false)
	  @SequenceGenerator(
		  name = "genders_sequence",
		  sequenceName = "genders_sequence",
		  allocationSize = 1
	  )
	  @GeneratedValue(
			  strategy = GenerationType.SEQUENCE,
			  generator = "genders_sequence"
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

	  public Gender() {}

	public Gender(Long id, String name, Boolean is_deleted) {
		this.id = id;
		this.name = name;
		this.is_deleted = is_deleted;
	}

	public Gender(String name, Boolean is_deleted) {
		this.name = name;
		this.is_deleted = is_deleted;
	}

	public Gender(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Boolean getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Gender{" +
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
