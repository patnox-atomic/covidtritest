package com.patnox.covidtritest.triage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.patnox.covidtritest.patients.Patient;
import com.patnox.covidtritest.priorities.Priority;
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
@Entity(name = "triage")
@Table(name = "triage")
public class Triage
{
	  @Id
	  @Column(name = "id", unique = true, nullable = false)
	  @SequenceGenerator(
		  name = "triage_sequence",
		  sequenceName = "triage_sequence",
		  allocationSize = 1
	  )
	  @GeneratedValue(
			  strategy = GenerationType.SEQUENCE,
			  generator = "triage_sequence"
	  )
	  private Long id;

//	  @NotNull
//	  @Column(name = "product_id")
//	  private Long product_id;
	  //@ManyToOne(optional = false)
	  //@OneToOne(cascade = CascadeType.MERGE)
	  @OneToOne(fetch = FetchType.EAGER,
      cascade = {
              CascadeType.MERGE,
              CascadeType.REFRESH
          })
	  @JoinColumn(name = "patient_id", nullable = false, referencedColumnName = "id")
	  private Patient patient;

	//	  @NotNull
//	  @Column(name = "product_id")
//	  private Long product_id;
	//@ManyToOne(optional = false)
	//@OneToOne(cascade = CascadeType.MERGE)
	@OneToOne(fetch = FetchType.EAGER,
			cascade = {
					CascadeType.MERGE,
					CascadeType.REFRESH
			})
	@JoinColumn(name = "priority_id", nullable = false, referencedColumnName = "id")
	private Priority priority;

	@NotBlank
	@NotNull
	@Column(name = "blood_pressure", length = 254)
	@Size(min = 1, max = 254)
	@Length(min = 1, max = 254)
	private String blood_pressure;

	@NotBlank
	@NotNull
	@Column(name = "temperature", length = 254)
	@Size(min = 1, max = 254)
	@Length(min = 1, max = 254)
	private String temperature;

	@NotBlank
	@NotNull
	@Column(name = "weight", length = 254)
	@Size(min = 1, max = 254)
	@Length(min = 1, max = 254)
	private String weight;

	@Column(name = "misc_symptoms", length = 1024)
	@Size(min = 0, max = 1024)
	@Length(min = 0, max = 1024)
	private String misc_symptoms;

	@Column(name = "is_treated" ,columnDefinition = "boolean default false")
	private Boolean is_treated;

	  @Column(name = "date_of_triage", columnDefinition="DATE DEFAULT CURRENT_DATE")
	  //@Temporal(TemporalType.DATE)
	  @JsonFormat(pattern="yyyy-MM-dd", timezone="Africa/Nairobi")
	  private LocalDate date_of_triage;

	  @Column(name = "is_deleted" ,columnDefinition = "boolean default false")
	  private Boolean is_deleted;

	  public Triage() {}

	public Triage(Long id, Patient patient, Priority priority, String blood_pressure, String temperature, String weight, String misc_symptoms, Boolean is_treated, LocalDate date_of_triage, Boolean is_deleted) {
		this.id = id;
		this.patient = patient;
		this.priority = priority;
		this.blood_pressure = blood_pressure;
		this.temperature = temperature;
		this.weight = weight;
		this.misc_symptoms = misc_symptoms;
		this.is_treated = is_treated;
		this.date_of_triage = date_of_triage;
		this.is_deleted = is_deleted;
	}

	public Triage(Patient patient, Priority priority, String blood_pressure, String temperature, String weight, String misc_symptoms, Boolean is_treated, LocalDate date_of_triage, Boolean is_deleted) {
		this.patient = patient;
		this.priority = priority;
		this.blood_pressure = blood_pressure;
		this.temperature = temperature;
		this.weight = weight;
		this.misc_symptoms = misc_symptoms;
		this.is_treated = is_treated;
		this.date_of_triage = date_of_triage;
		this.is_deleted = is_deleted;
	}

	public Triage(Patient patient, Priority priority, String blood_pressure, String temperature, String weight) {
		this.patient = patient;
		this.priority = priority;
		this.blood_pressure = blood_pressure;
		this.temperature = temperature;
		this.weight = weight;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public String getBlood_pressure() {
		return blood_pressure;
	}

	public void setBlood_pressure(String blood_pressure) {
		this.blood_pressure = blood_pressure;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getMisc_symptoms() {
		return misc_symptoms;
	}

	public void setMisc_symptoms(String misc_symptoms) {
		this.misc_symptoms = misc_symptoms;
	}

	public Boolean getIs_treated() {
		return is_treated;
	}

	public void setIs_treated(Boolean is_treated) {
		this.is_treated = is_treated;
	}

	public LocalDate getDate_of_triage() {
		return date_of_triage;
	}

	public void setDate_of_triage(LocalDate date_of_triage) {
		this.date_of_triage = date_of_triage;
	}

	public Boolean getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	@Override
	public String toString() {
		return "Triage{" +
				"id=" + id +
				", patient=" + patient +
				", priority=" + priority +
				", blood_pressure='" + blood_pressure + '\'' +
				", temperature='" + temperature + '\'' +
				", weight='" + weight + '\'' +
				", misc_symptoms='" + misc_symptoms + '\'' +
				", is_treated=" + is_treated +
				", date_of_triage=" + date_of_triage +
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
