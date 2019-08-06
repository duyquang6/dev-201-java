package vn.kms.launch.cleancode;

import java.io.*;
import java.util.*;

public class BadApplication {
	private static final Set<String> mValidStateCodes = new HashSet<>(
			Arrays.asList("AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "GU", "HI", "ID",
					"IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MH", "MA", "MI", "FM", "MN", "MS", "MO", "MT",
					"NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI",
					"SC", "SD", "TN", "TX", "UT", "VT", "VA", "VI", "WA", "WV", "WI", "WY"));
	private static int yearOfReport = 2016;
	private static final Map<String, String> reportHeaders = new HashMap<String, String>() {
		{
			put("invalid-contact-details", "contact_id\terror_field\terror_message\r\n");
			put("invalid-contact-summary", "field_name\tnumber_of_invalid_contact\r\n");
			put("contact-per-state", "state_code\tnumber_of_contact\r\n");
			put("contact-per-age-group", "group\tnumber_of_contact\tpercentage_of_contact\r\n");
		}
	};

	public static void main(String[] args) throws Exception {
		Map<String, Integer> fieldErrorCounts = new TreeMap<>(); // I want to sort by key
		Map<Integer, Map<String, String>> invalidContacts = new LinkedHashMap<>(); // invalidContacts order by ID
		Map reports = loadFileAndConvertToEntityAndValidateEntityAndStoreEntityAndReturnReports(fieldErrorCounts,
				invalidContacts);

		// 5. Store reports
		for (Object object : reportHeaders.keySet()) {
			String reportName = (String) object;
			File outputFile = new File("output");
			if (!outputFile.exists()) {
				outputFile.mkdirs();
			}

			Writer writer = new FileWriter(new File(outputFile, reportName + ".tab"));

			// write header
			writer.write(reportHeaders.get(reportName));

			Map<String, Integer> report = (Map<String, Integer>) reports.get(reportName);
			if (reportName.equals("contact-per-age-group")) {
				int total = 0;
				for (Integer v : report.values()) {
					total += v;
				}
				// I want to sort by age-group followed the requirement
				String[] ageGroups = { "Children", "Adolescent", "Adult", "Middle Age", "Senior" };
				for (String item : ageGroups) {
					writer.write(item + "\t" + report.get(item) + "\t" + (int) ((report.get(item) * 100.0f) / total)
							+ "%\r\n");
				}
			} else if (reportName.equals("contact-per-state")) {
				report = new TreeMap<>(report); // I want to sort by state
				for (String item : report.keySet()) {
					writer.write(item + "\t" + report.get(item) + "\r\n");
				}
			} else if (reportName.equals("invalid-contact-summary")) {
				for (Map.Entry entry : fieldErrorCounts.entrySet()) {
					writer.write(entry.getKey() + "\t" + entry.getValue() + "\r\n");
				}
			} else { // invalid-contact-details
				for (Map.Entry<Integer, Map<String, String>> entry : invalidContacts.entrySet()) {
					int contactID = entry.getKey();
					Map<String, String> errorByFields = entry.getValue();
					for (String field_name : errorByFields.keySet()) {
						writer.write(contactID + "\t" + field_name + "\t" + errorByFields.get(field_name) + "\r\n");
					}
				}
			}
			writer.flush();
			System.out.println("Generated report " + "output/" + reportName + ".tab");
		}
	}

	/**
	 * @return array of total-lines, blank-lines, invalid-lines, valid-entities,
	 *         invalid-entities, total-errors
	 */
	private static Map loadFileAndConvertToEntityAndValidateEntityAndStoreEntityAndReturnReports(
			Map<String, Integer> counts, Map<Integer, Map<String, String>> invalidContacts) throws Exception {
		// 1. Load data from file
		InputStream is = new FileInputStream("data/contacts.tsv");
		char[] buff = new char[100000]; // guest file size is not greater than 100000 chars
		int character;
		int totalCharsInFile = 0; // count total characters in file
		while ((character = is.read()) != -1) {
			buff[totalCharsInFile] = (char) character;
			totalCharsInFile++;
		}
        // all data from file load to string s
		String s = new String(buff, 0, totalCharsInFile);
		String[] lines = s.split("\r"); // get all lines
		List<Contact> allContacts = new ArrayList<>();
		for (int i = 0; i < lines.length; i++) {
			// ignore header line
			if (i == 0) { 
				continue;
			}
			// ignore blank lines
			if (lines[i].trim().length() == 0) {
				continue;
			}
			String[] data = lines[i].split("\t"); // get data of a line
			// invalid line format
			if (data.length != 14) { 
				continue;
			}
			// TODO: I will use reflection & annotations to build contact object later
			Person person = new Person();
			Address address = new Address();
			Contact contact = new Contact(person, address);
			try {
				// FIXME: Don't forget to change this code if first column is not ID
				contact.setId(Integer.parseInt(data[0]));
			} catch (Exception ex) {
				System.out.println("It is not ID format");
				continue;
			}

			// FIXME: need to change this code if the order of column may be changed
			person.setFirstName(data[1]);
			person.setLastName(data[2]);
			person.setDayOfBirth(data[4]);
			address.setHouseNumberAndStreet(data[5]);
			address.setCity(data[6]);
			address.setState(data[8]);
			address.setZipCode(data[9]);
			contact.setPhoneNumber(data[10]);
			contact.setEmail(data[12]);

			if (contact != null) {
				allContacts.add(contact);
			} else {
				// for some reason, I think contact object may be null (I am not sure, but I
				// want to log for sure!!!)
				System.out.println("Contact is null, don't know why!!!");
			}
		}

		// 2. Validate contact data
		// TODO: I will use refection & annotations to isValid contact data later
		for (Contact contact : allContacts) {
			Map<String, String> errors = new TreeMap<>(); // errors order by field name
			if (contact.getPerson().getFirstName().trim().length() == 0) {
				errors.put("firstName", "is empty");
				addFieldERROR(counts, "first_name");
			}
			if (contact.getPerson().getFirstName().length() > 10) {
				errors.put("firstName", "'" + contact.getPerson().getFirstName() + "''s length is over 10");
				addFieldERROR(counts, "first_name");
			}
			if (contact.getPerson().getLastName().trim().length() == 0) {
				errors.put("lastName", "is empty");
				addFieldERROR(counts, "last_name");
			}
			if (contact.getPerson().getLastName().length() > 10) {
				errors.put("lastName", "'" + contact.getPerson().getLastName() + "''s length is over 10");
				addFieldERROR(counts, "last_name");
			}
			if (contact.getPerson().getDayOfBirth() == null || contact.getPerson().getDayOfBirth().trim().length() != 10) {
				errors.put("day_of_birth", "'" + contact.getPerson().getDayOfBirth() + "' is invalid");
				addFieldERROR(counts, "day_of_birth");
			}
			if (contact.getAddress().getHouseNumberAndStreet().length() > 20) {
				errors.put("address", "'" + contact.getAddress().getHouseNumberAndStreet() + "''s length is over 20");
				addFieldERROR(counts, "address");
			}
			if (contact.getAddress().getCity().length() > 15) {
				errors.put("city", "'" + contact.getAddress().getCity() + "''s length is over 15");
				addFieldERROR(counts, "city");
			}
			if (!mValidStateCodes.contains(contact.getAddress().getState())) {
				errors.put("state", "'" + contact.getAddress().getState() + "' is incorrect state code");
				addFieldERROR(counts, "state");
			}
			if (!contact.getAddress().getZipCode().matches("^\\d{4,5}$")) {
				errors.put("zipCode", "'" + contact.getAddress().getZipCode() + "' is not four or five digits");
				addFieldERROR(counts, "zip_code");
			}
			if (!contact.getPhoneNumber().matches("^\\d{3}\\-\\d{3}\\-\\d{4}$")) {
				errors.put("mobilePhone", "'" + contact.getPhoneNumber() + "' is invalid format XXX-XXX-XXXX");
				addFieldERROR(counts, "mobile_phone");
			}
			if (!contact.getEmail().matches("^.+@.+\\..+$")) {
				errors.put("email", "'" + contact.getEmail() + "' is invalid email format");
				addFieldERROR(counts, "email");
			}

			if (!errors.isEmpty()) {
				invalidContacts.put(contact.getId(), errors);
			} else { // populate other fields from raw fields
				contact.getPerson().setAge(calculateAgeByYear(contact.getPerson().getDayOfBirth()));
			}
		}

		// 3. Sort contact by zipcode
		// TODO: need to change this code if we use other field to sort the list
		for (int i = 0; i < allContacts.size(); i++) {
			for (int j = allContacts.size() - 1; j > i; j--) {
				Contact contactA = allContacts.get(i);
				Contact contactB = allContacts.get(j);
				if (!invalidContacts.containsKey(contactA.getId())
						&& !invalidContacts.containsKey(contactB.getId())) {
					int zipCodeContactA = Integer.parseInt(contactA.getAddress().getZipCode());
					int zipCodeContactB = Integer.parseInt(contactB.getAddress().getZipCode());
					if (zipCodeContactA > zipCodeContactB) {
						allContacts.set(i, contactB);
						allContacts.set(j, contactA);
					}
				}
			}
		}

		// 4. Store valid data
		File outputFile = new File("output");
		if (!outputFile.exists()) {
			outputFile.mkdirs();
		}

		try(Writer writer1 = new FileWriter(new File(outputFile, "valid-contacts.tab"))) {
			// write header
			writer1.write(
					"id\tfirst_name\tlast_name\tday_of_birth\taddress\tcity\tstate\tzip_code\tmobile_phone\temail\r\n");
			for (Contact contact : allContacts) {
				if (!invalidContacts.containsKey(contact.getId())) {
					writer1.write(contact.toString() + "\r\n");
				}
			}
			writer1.flush();
		} catch (Exception e) {
			System.out.println("Storing valid data got error");
		}

		// 5. Generate contact per state report
		Map reports = new HashMap<>();
		Map<String, Integer> contactsByStates = new HashMap<>();
		Map<String, Integer> contactsByAgeGroups = new HashMap<>();
		for (Contact contact : allContacts) {
			if (!invalidContacts.containsKey(contact.getId())) {
				int stateCount = 0;
				if (contactsByStates.containsKey(contact.getAddress().getState())) {
					stateCount = contactsByStates.get(contact.getAddress().getState());
				}
				contactsByStates.put(contact.getAddress().getState(), stateCount + 1);

				int ageGroupCount = 0;
				if (contactsByAgeGroups.containsKey(convertAgeToGroup(contact.getPerson().getAge()))) {
					ageGroupCount = contactsByAgeGroups.get(convertAgeToGroup(contact.getPerson().getAge()));
				}
				contactsByAgeGroups.put(convertAgeToGroup(contact.getPerson().getAge()), ageGroupCount + 1);
			}
		}

		reports.put("contact-per-state", contactsByStates);
		reports.put("contact-per-age-group", contactsByAgeGroups);
		return reports;
	}

	private static void addFieldERROR(Map<String, Integer> counts, String fieldName) {
		Integer count = counts.get(fieldName);
		if (count == null) {
			count = 0;
		}
		count = count + 1;
		counts.put(fieldName, count);
	}

	/**
	 * Calculate the age of contact by year. It's not accurate but acceptable
	 * 
	 * @param dateOfBirth
	 * @return
	 */
	public static int calculateAgeByYear(String dateOfBirth) {
		String yearStr = dateOfBirth.split("/")[2];
		int year = Integer.parseInt(yearStr);
		return yearOfReport - year;
	}

	// TODO: Calculate age exactly by month/day/year.
	// Chưa hiểu đề
	public static int preciseCalculateAge(String dateOfBirth) {
		return 10;
	}

	private static String convertAgeToGroup(int age) {
		if (age <= 9) {
			return "Children";
		} else if (age <= 19) {
			return "Adolescent";
		} else if (age <= 45) {
			return "Adult";
		} else if (age <= 60) {
			return "Middle Age";
		} else {
			return "Senior";
		}
	}
}
