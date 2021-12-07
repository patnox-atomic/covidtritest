package com.patnox.covidtritest.patients;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.patnox.covidtritest.countries.Country;
import com.patnox.covidtritest.genders.Gender;
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
@Entity(name = "patients")
@Table(name = "patients")
public class Patient
{
	  @Id
	  @Column(name = "id", unique = true, nullable = false)
	  @SequenceGenerator(
		  name = "patients_sequence",
		  sequenceName = "patients_sequence",
		  allocationSize = 1
	  )
	  @GeneratedValue(
			  strategy = GenerationType.SEQUENCE,
			  generator = "patients_sequence"
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
	  @JoinColumn(name = "country_id", nullable = false, referencedColumnName = "id")
	  private Country country;

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
	@JoinColumn(name = "gender_id", nullable = false, referencedColumnName = "id")
	private Gender gender;

	@NotBlank
	@NotNull
	@Column(name = "first_name", length = 254)
	@Size(min = 1, max = 254)
	@Length(min = 1, max = 254)
	private String first_name;

	@NotBlank
	@NotNull
	@Column(name = "last_name", length = 254)
	@Size(min = 1, max = 254)
	@Length(min = 1, max = 254)
	private String last_name;

	@NotBlank
	@NotNull
	@Column(name = "address", length = 254)
	@Size(min = 1, max = 254)
	@Length(min = 1, max = 254)
	private String address;

	@NotBlank
	@NotNull
	@Column(name = "email", length = 254)
	@Size(min = 1, max = 254)
	@Length(min = 1, max = 254)
	private String email;

	@NotBlank
	@NotNull
	@Column(name = "phone", length = 254)
	@Size(min = 1, max = 254)
	@Length(min = 1, max = 254)
	private String phone;

	@Column(name = "date_of_birth", columnDefinition="DATE DEFAULT CURRENT_DATE")
	//@Temporal(TemporalType.DATE)
	@JsonFormat(pattern="yyyy-MM-dd", timezone="Africa/Nairobi")
	private LocalDate date_of_birth;

	  @Column(name = "date_registered", columnDefinition="DATE DEFAULT CURRENT_DATE")
	  //@Temporal(TemporalType.DATE)
	  @JsonFormat(pattern="yyyy-MM-dd", timezone="Africa/Nairobi")
	  private LocalDate date_registered;

	  @Column(name = "is_deleted" ,columnDefinition = "boolean default false")
	  private Boolean is_deleted;

	  public Patient() {}

	public Patient(Long id, Country country, Gender gender, String first_name, String last_name, String address, String email, String phone, LocalDate date_of_birth, LocalDate date_registered, Boolean is_deleted) {
		this.id = id;
		this.country = country;
		this.gender = gender;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.date_of_birth = date_of_birth;
		this.date_registered = date_registered;
		this.is_deleted = is_deleted;
	}

	public Patient(Country country, Gender gender, String first_name, String last_name, String address, String email, String phone, LocalDate date_of_birth, LocalDate date_registered, Boolean is_deleted) {
		this.country = country;
		this.gender = gender;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.date_of_birth = date_of_birth;
		this.date_registered = date_registered;
		this.is_deleted = is_deleted;
	}

	public Patient(Country country, Gender gender, String first_name, String last_name, String address, String email, String phone, LocalDate date_of_birth) {
		this.country = country;
		this.gender = gender;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.date_of_birth = date_of_birth;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public LocalDate getDate_registered() {
		return date_registered;
	}

	public void setDate_registered(LocalDate date_registered) {
		this.date_registered = date_registered;
	}

	public Boolean getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(Boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	@Override
	public String toString() {
		return "Patient{" +
				"id=" + id +
				", country=" + country +
				", gender=" + gender +
				", first_name='" + first_name + '\'' +
				", last_name='" + last_name + '\'' +
				", address='" + address + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", date_of_birth=" + date_of_birth +
				", date_registered=" + date_registered +
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
