package com.vermeg.ams.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
//@Table(name = "client")
public class Client {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name = "id_client")
	private int idClient;
	
	@Column (name="firstName")
	//@NotBlank (message="required")
	private String firstName;
	
	@Column (name="lastName")
	//@NotBlank (message="required")
	private String lastName;
	
	@Column (name="adress")
	//@NotBlank (message="required")
	private String adress;
	
	@Column(name="active")
	private int active;
	
	@Column(name="password")
	private String password;
	
	@Column (name="mail",unique=true)
	//@NotBlank (message="required")
	private String mail;
	
	@Column (name="phoneNumber")
	//@NotBlank (message="required")
	private int phoneNumber;
	

	
	@OneToMany (mappedBy="client_c",fetch=FetchType.LAZY,
			cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH,CascadeType.REMOVE}
			)
	@JsonIgnore
	private List <Order> orders;
	

	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	public Client() {}
	
	
	
	public Client(String firstName, String lastName, String adress, String password, String mail, int phoneNumber,
			String isadmin) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.adress = adress;
		this.password = password;
		this.mail = mail;
		this.phoneNumber = phoneNumber;
		

	}
	

	

	
	
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIdClient() {
		return idClient;
	}
	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getln() {
		
		return this.getFirstName() + " "+ this.getLastName();
		
	}
	@ManyToMany(cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> role;


	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}

	
	public StringBuilder getmyrole() {
		
		StringBuilder s = new StringBuilder();
		if(this.getRole()!=null)
		{
		for(Role r:this.getRole()) {
			s.append(r.getRole().toString());
			}
		}else {System.out.println("**********************************************************************");}
	 return s;
	}
}
