package vn.kms.launch.cleancode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static vn.kms.launch.cleancode.Util.callSetMethods;
import static vn.kms.launch.cleancode.Util.columnHeadersToString;

public class Contact {
	@Column(header = "id")
	private int id;
	private Person person;
	private Address address;
	@Column(header = "phone1")
	private String phoneNumber;
	@Column(header = "email")
	private String email;

	public Contact() {
		super();
		this.person = new Person();
		this.address = new Address();
	}

	public Contact(Person person, Address address) {
		this.person = person;
		this.address = address;
	}

	public int getId() {
		return id;
	}

	@Column(header = "id")
	public void setId(int id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public Address getAddress() {
		return address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Column(header = "phone1")
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	@Column(header = "email")
	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return String.format("%s\t%s\t%s\t%s\t%s", id, person.toString(), address.toString(), phoneNumber, email);
	}

	public void loadDataToEntity(Map<String, Integer> headerIndexByFieldName, String[] dataColumns) {
		callSetMethods(person,dataColumns,headerIndexByFieldName);
		callSetMethods(address,dataColumns,headerIndexByFieldName);
		callSetMethods(this,dataColumns,headerIndexByFieldName);
	}
}