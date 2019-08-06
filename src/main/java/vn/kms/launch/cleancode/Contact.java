package vn.kms.launch.cleancode;

public class Contact {
	private int id;
	private Person person;
	private Address address;
	private String phoneNumber;
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

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return String.format("%s\t%s\t%s\t%s\t%s", id, person.toString(), address.toString(), phoneNumber, email);
	}
}
