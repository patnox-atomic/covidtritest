package com.patnox.covidtritest.priorities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@DynamicInsert
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "priorities")
@Table(name = "priorities")
public class Priority
{
	  @Id
	  @Column(name = "id", unique = true, nullable = false)
	  @SequenceGenerator(
		  name = "priorities_sequence",
		  sequenceName = "priorities_sequence",
		  allocationSize = 1
	  )
	  @GeneratedValue(
			  strategy = GenerationType.SEQUENCE,
			  generator = "priorities_sequence"
	  )
	  private Long id;

	  @NotNull
	  @Min(1)
	  @Column(name = "rank")
	  private Long rank;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "description")
	private String description;

	  @Column(name = "is_deleted" ,columnDefinition = "boolean default false")
	  private Boolean is_deleted;

	  public Priority() {}

	public Priority(Long id, Long rank, String name, String description, Boolean is_deleted) {
		this.id = id;
		this.rank = rank;
		this.name = name;
		this.description = description;
		this.is_deleted = is_deleted;
	}

	public Priority(Long rank, String name, String description, Boolean is_deleted) {
		this.rank = rank;
		this.name = name;
		this.description = description;
		this.is_deleted = is_deleted;
	}

	public Priority(Long rank, String name, String description) {
		this.rank = rank;
		this.name = name;
		this.description = description;
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

	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Priority{" +
				"id=" + id +
				", rank=" + rank +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
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
